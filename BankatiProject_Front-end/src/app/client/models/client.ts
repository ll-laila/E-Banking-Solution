import {PaymentAccount} from './paymentAccount';

export interface Client {
  id : number;
  firstName : string;
  lastName : string;
  email : string;
  phoneNumber : string;
  paymentAccount: PaymentAccount;
  currency:string;

}
