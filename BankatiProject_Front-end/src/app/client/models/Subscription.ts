
export interface Transaction {
  userId: string; // Identifiant de l'envoyeur
  agentId: string; // Identifiant du bénéficiaire
  price: number; // Montant de la transaction
  durationInMonths: number;
}
