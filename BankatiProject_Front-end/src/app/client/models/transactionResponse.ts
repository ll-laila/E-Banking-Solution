
export interface TransactionResponse {

   id: string;
   amount: number;
   transactionMethod: string;
   beneficiaryId: string;
   beneficiaryName: string;
   beneficiaryPhone: string;
   beneficiaryRole: string;
   transactionType: string;
   status: string;
   senderCurrency: string;
   beneficiaryCurrency: string;
   createdDate: string;
   senderId: string;
   senderName: string;
   senderPhoneNumber: string;
   senderRole: string;
   lastModifiedDate: string;
   validatedDate: string;
}
