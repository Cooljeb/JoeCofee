/** Contrat d'entrée aligné sur ConsommationDtoIn du backend Java. */
export interface ConsumptionInput {
  reglageBroyeur: number
  reglageIntensite: number
  cafeId: number
  machineACafeId: number
}

/**
 * Représentation minimale utilisée par le socle Front.
 * Les champs supplémentaires du DTO OUT seront complétés dans l'issue écran
 * au moment où ils auront un usage UX concret.
 */
export interface Consumption extends ConsumptionInput {
  codeConsommation: number
}
