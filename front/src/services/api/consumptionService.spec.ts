import { afterEach, describe, expect, it, vi } from 'vitest'
import { consumptionService } from './consumptionService'

/**
 * Ces tests protègent le contrat REST au niveau du service métier : si une URL
 * change accidentellement, une View n'a pas besoin d'être montée pour le voir.
 */
describe('consumptionService', () => {
  afterEach(() => vi.restoreAllMocks())

  it('charge l’historique via le endpoint centralisé', async () => {
    const fetchMock = vi.fn().mockResolvedValue(new Response(JSON.stringify([]), {
      status: 200,
      headers: { 'Content-Type': 'application/json' }
    }))
    vi.stubGlobal('fetch', fetchMock)

    await consumptionService.getAll()

    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringMatching(/\/consommations$/),
      expect.objectContaining({ headers: expect.any(Object) })
    )
  })

  it('envoie une nouvelle consommation en POST JSON', async () => {
    const fetchMock = vi.fn().mockResolvedValue(new Response(JSON.stringify({ codeConsommation: 1 }), {
      status: 200,
      headers: { 'Content-Type': 'application/json' }
    }))
    vi.stubGlobal('fetch', fetchMock)

    const payload = { cafeId: 2, machineACafeId: 3, reglageBroyeur: 4, reglageIntensite: 5 }
    await consumptionService.create(payload)

    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringMatching(/\/consommations$/),
      expect.objectContaining({ method: 'POST', body: JSON.stringify(payload) })
    )
  })
})
