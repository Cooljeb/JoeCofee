/** Contrat de lecture aligné sur `MachineACafeDtoOut`. */
export interface Machine {
  id: number
  nomCommercial: string
  referenceCommerciale: string
  description: string
  marque: string
}

/** Corps POST/PUT strictement aligné sur `MachineACafeDtoIn`. */
export interface MachineInput {
  nomCommercial: string
  referenceCommerciale: string
  description: string
  marqueId: number
}
