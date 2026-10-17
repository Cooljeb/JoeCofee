// @vitest-environment jsdom
import { afterEach, describe, expect, it, vi } from 'vitest'
import { ApiError, apiRequest } from './httpClient'

describe('apiRequest', () => {
  afterEach(() => {
    vi.unstubAllGlobals()
    vi.restoreAllMocks()
  })

  it('retourne le JSON pour une réponse HTTP valide', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(
      new Response(JSON.stringify({ id: 7 }), {
        status: 200,
        headers: { 'Content-Type': 'application/json' }
      })
    ))

    await expect(apiRequest<{ id: number }>('/test')).resolves.toEqual({ id: 7 })
    expect(fetch).toHaveBeenCalledWith(
      'http://localhost:8080/api/test',
      expect.objectContaining({ headers: expect.objectContaining({ 'Content-Type': 'application/json' }) })
    )
  })

  it('accepte un 204 sans tenter de parser un corps vide', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 204 })))
    await expect(apiRequest<void>('/test/1', { method: 'DELETE' })).resolves.toBeUndefined()
  })

  it('normalise une erreur HTTP en ApiError', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 404 })))
    await expect(apiRequest('/missing')).rejects.toMatchObject<ApiError>({ status: 404 })
  })

  it('normalise une panne réseau sans exposer le détail technique', async () => {
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new Error('socket secret')))
    await expect(apiRequest('/offline')).rejects.toMatchObject<ApiError>({
      status: 0,
      message: 'Backend indisponible'
    })
  })
})
