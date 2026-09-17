const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080/api'

/** Erreur normalisée : les vues n'ont pas à connaître les détails de fetch. */
export class ApiError extends Error {
  constructor(public readonly status: number, message: string) {
    super(message)
  }
}

/**
 * Point d'entrée HTTP unique du Front.
 * Centraliser ici l'URL, les headers et les erreurs évite que chaque composant
 * réimplémente sa propre façon de parler au backend Spring.
 */
export async function apiRequest<T>(path: string, options: RequestInit = {}): Promise<T> {
  const method = options.method ?? 'GET'
  console.debug(`[API] ${method} ${path}`)

  try {
    const response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers: { 'Content-Type': 'application/json', ...options.headers }
    })

    if (!response.ok) {
      console.warn(`[API] ${method} ${path} -> HTTP ${response.status}`)
      throw new ApiError(response.status, `Erreur API (${response.status})`)
    }

    // DELETE peut répondre 204 sans corps : on ne tente pas de parser du JSON vide.
    if (response.status === 204) return undefined as T
    return await response.json() as T
  } catch (error) {
    if (error instanceof ApiError) throw error
    console.error(`[API] Backend indisponible pour ${method} ${path}`, error)
    throw new ApiError(0, 'Backend indisponible')
  }
}
