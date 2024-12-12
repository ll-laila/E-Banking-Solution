export interface TransactionCrypto {
  id: string; // Identifiant unique de la transaction
  userBuyId: string; // ID de l'utilisateur qui achète
  cryptoName: string; // Nom de la cryptomonnaie
  amount: number; // Quantité échangée
  price: number; // Prix unitaire
  transactionType: string; // Type de transaction (achat ou vente, par exemple)
  timestamp: string; // Date et heure de la transaction (format ISO 8601 recommandé)
}
