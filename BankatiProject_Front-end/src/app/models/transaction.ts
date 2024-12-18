export interface Transaction {
  id: string;
  amount: number;
  transactionMethod: string | null;
  beneficiaryId: string;
  beneficiaryName: string;
  beneficiaryPhone: string;
  beneficiaryRole: string;
  transactionType: string;
  status: string;
  senderCurrency: string;
  beneficiaryCurrency: string;
  createdDate: string | null;
  senderId: string;
  senderName: string;
  senderPhoneNumber: string;
  senderRole: string;
  lastModifiedDate: string | null;
  validatedDate: string | null;
}
