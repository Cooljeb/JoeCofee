/**
 * Contrat de lecture aligné sur `CafeDtoOut` du backend Java.
 *
 * Point important quand on vient de Java : une `interface` TypeScript ne crée
 * aucun objet à l'exécution. Elle décrit la forme des données JSON attendues
 * afin que l'IDE et le compilateur puissent détecter les incohérences.
 */
/** Valeurs JSON exactes des enums Spring TypeCafe et LabelCafe. */
export type CoffeeType = 'ARABICA' | 'ROBUSTA' | 'BLEND'
export type CoffeeLabel = 'BIO' | 'FAIR_TRADE' | 'RAINFOREST_ALLIANCE' | 'UTZ' | 'BIO_FAIR_TRADE' | 'NONE'

export interface Coffee {
  id: number
  nomCafe: string
  description: string
  typeCafe: CoffeeType
  labelCafe: CoffeeLabel | null
  commercant: number
  commercantNom: string
  commercantType: string
}

/** Corps POST/PUT strictement aligné sur `CafeDtoIn`. */
export interface CoffeeInput {
  nomCafe: string
  description: string
  typeCafe: CoffeeType
  labelCafe: CoffeeLabel | null
  commercant: number
}
