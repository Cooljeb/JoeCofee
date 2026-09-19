/**
 * Contrat de lecture aligné sur `CafeDtoOut` du backend Java.
 *
 * Point important quand on vient de Java : une `interface` TypeScript ne crée
 * aucun objet à l'exécution. Elle décrit la forme des données JSON attendues
 * afin que l'IDE et le compilateur puissent détecter les incohérences.
 */
export interface Coffee {
  id: number
  nomCafe: string
  description: string
  typeCafe: string
  labelCafe: string
  commercant: number
  commercantNom: string
  commercantType: string
}
