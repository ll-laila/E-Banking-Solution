import {TransactionType} from "./transaction-type";

export interface Transaction {
  senderId: string; // Identifiant de l'envoyeur
  beneficiaryId: string; // Identifiant du bénéficiaire
  amount: number; // Montant de la transaction
  transactionType: TransactionType; // Type de transaction (ex : PAYMENT, TRANSFER)
}
