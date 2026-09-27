export interface Distributor {
  id: number
  nom: string
  adresse: string
  email: string
  telephone: string
  siteInternet: string
  nomDuGroupeDeDistribution: string
}

export type DistributorInput = Omit<Distributor, 'id'>
