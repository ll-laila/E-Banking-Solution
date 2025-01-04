export interface VirtualCard {
  id: string;
  cardNumber: string;
  userId: string;
  expirationDate: string;
  status: 'ACTIVE' | 'INACTIVE' | 'EXPIRED';
  createdAt: string;
  updatedAt: string;
  montant: string;
}
