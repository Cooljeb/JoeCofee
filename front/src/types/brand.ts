/** Contrats strictement alignés sur MarqueDtoOut / MarqueDtoIn côté Spring. */
export interface Brand {
  id: number
  marque: string
}

export interface BrandInput {
  marque: string
}
