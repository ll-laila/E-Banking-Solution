import {TransactionStatus} from "./transaction-status.enum";
import {TransactionType} from "./transaction-type.enum";

export interface Transaction {
  id: string;
  amount: number; // Montant de la transaction
  beneficiaryId: string; // Identifiant du bénéficiaire
  beneficiaryName: string; // Nom du bénéficiaire
  beneficiaryPhone: string; // Téléphone du bénéficiaire
  beneficiaryRole: string; // Rôle du bénéficiaire (ex : CLIENT, AGENT)
  transactionType: TransactionType; // Type de transaction (ex : PAYMENT, TRANSFER)
  status: TransactionStatus; // Statut initial de la transaction
  beneficiaryCurrency: string; // Devise (EUR, USD, MAD)
  validatedDate?: string; // Date de validation, si applicable
  senderId: string; // Identifiant de l'envoyeur
  senderName: string; // Nom de l'envoyeur
  senderPhoneNumber: string; // Téléphone de l'envoyeur
  senderRole: string; // Rôle de l'envoyeur
  senderCurrency: string; // Devise de l'envoyeur
}
