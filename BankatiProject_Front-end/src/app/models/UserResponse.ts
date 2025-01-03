export interface UserResponse {
  id: string;
  agentId: string | null;
  firstName: string;
  lastName: string;
  email: string;
  address: string | null;
  cin: string | null;
  birthDate: string | null; // ou un type Date si vous préférez
  phoneNumber: string;
  role: string; // Peut être 'ADMIN', 'AGENT', ou 'CLIENT'
  password: string; // (si vous en avez besoin, mais pensez à éviter de l'exposer dans les réponses)
  isFirstLogin: boolean;
  commercialRn: string | null;
  image: string | null;
  patentNumber: string | null;
  isPaymentAccountActivated: boolean | null;
  typeHissab: string | null;
  currency: string | null;
}
