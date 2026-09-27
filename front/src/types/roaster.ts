export interface Roaster {
  id: number
  nom: string
  adresse: string
  email: string
  telephone: string
  siteInternet: string
  anneeCreation: string
}

export type RoasterInput = Omit<Roaster, 'id'>
