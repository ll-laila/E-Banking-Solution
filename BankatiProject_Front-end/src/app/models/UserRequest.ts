export interface UserRequest {
  id: string;
  agentId: string;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
  cin: string;
  birthDate: Date;
  phoneNumber: string;
  role: Role;
  password: string;
  isFirstLogin: boolean;
  commercialRn: string;
  image: string;
  patentNumber: string;
  isPaymentAccountActivated: boolean;
  typeHissab: string;
  currency: string;
  token: string;
}


export enum Role {
  ADMIN = 'ADMIN',
  CLIENT = 'CLIENT',
  AGENT = 'AGENT',
}
