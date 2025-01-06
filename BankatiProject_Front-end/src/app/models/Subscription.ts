export interface Subscription {
  id: string; // Identifiant unique de l'abonnement
  userId: string; // L'utilisateur lié à l'abonnement
  agentId: string; // L'agent lié à l'abonnement
  price: number; // Prix de l'abonnement
  startDate: string; // Date de début de l'abonnement (format ISO string)
  endDate: string; // Date de fin de l'abonnement (format ISO string)
  active: boolean; // Indique si l'abonnement est actif
}
