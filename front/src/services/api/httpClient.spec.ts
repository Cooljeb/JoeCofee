import { afterEach, describe, expect, it, vi } from 'vitest'
import { ApiError, apiRequest } from './httpClient'

describe('apiRequest', () => {
  afterEach(() => vi.restoreAllMocks())

  it('parse la réponse JSON en cas de succès', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(JSON.stringify({ id: 42 }), {
      status: 200,
      headers: { 'Content-Type': 'application/json' }
    })))

    await expect(apiRequest<{ id: number }>('/test')).resolves.toEqual({ id: 42 })
    expect(fetch).toHaveBeenCalledOnce()
  })

  it('accepte une réponse DELETE 204 sans tenter de parser du JSON', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 204 })))

    await expect(apiRequest<void>('/test/42', { method: 'DELETE' })).resolves.toBeUndefined()
  })

  it('normalise une erreur HTTP en ApiError', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 404 })))

    await expect(apiRequest('/absent')).rejects.toMatchObject<ApiError>({ status: 404 })
  })

  it('normalise une panne réseau sans exposer le détail technique aux vues', async () => {
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new TypeError('network down')))

    await expect(apiRequest('/test')).rejects.toMatchObject<ApiError>({
      status: 0,
      message: 'Backend indisponible'
    })
  })
})
