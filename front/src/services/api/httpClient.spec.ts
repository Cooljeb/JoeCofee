import { afterEach, describe, expect, it, vi } from 'vitest'
import { apiRequest } from './httpClient'

describe('apiRequest', () => {
  afterEach(() => {
    // Les stubs globaux de fetch doivent être retirés entre les cas de test.
    vi.unstubAllGlobals()
    vi.restoreAllMocks()
  })

  it('parse la réponse JSON en cas de succès', async () => {
    const debugSpy = vi.spyOn(console, 'debug').mockImplementation(() => {})
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(JSON.stringify({ id: 42 }), {
      status: 200,
      headers: { 'Content-Type': 'application/json' }
    })))

    await expect(apiRequest<{ id: number }>('/test')).resolves.toEqual({ id: 42 })
    expect(fetch).toHaveBeenCalledOnce()
    expect(debugSpy).not.toHaveBeenCalled()
  })

  it('accepte une réponse DELETE 204 sans tenter de parser du JSON', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 204 })))

    await expect(apiRequest<void>('/test/42', { method: 'DELETE' })).resolves.toBeUndefined()
  })

  it('normalise une erreur HTTP en ApiError', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response(null, { status: 404 })))

    await expect(apiRequest('/absent')).rejects.toMatchObject({ status: 404 })
  })

  it('normalise une panne réseau sans exposer le détail technique aux vues', async () => {
    const errorSpy = vi.spyOn(console, 'error').mockImplementation(() => {})
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new TypeError('token=never-log-me')))

    await expect(apiRequest('/test')).rejects.toMatchObject({
      status: 0,
      message: 'Backend indisponible'
    })
    // Les logs doivent aider au diagnostic sans contenir l'exception réseau brute.
    expect(errorSpy).toHaveBeenCalledOnce()
    expect(errorSpy.mock.calls.flat().join(' ')).not.toContain('token=never-log-me')
  })
})
