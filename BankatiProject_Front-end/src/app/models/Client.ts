export interface IClient {
  id: string;
  agentId: string;
  firstName: string;
  lastName: string;
  email: string
  address: string;
  cin: string;
  birthDate: Date;
  phoneNumber: string;
  password?: string;
  isFirstLogin: boolean;
  createdDate: Date;
  commercialRn: string;
  patentNumber: string;
  isPaymentAccountActivated: boolean;
  typeHissab : string;
  currency : string;
}

