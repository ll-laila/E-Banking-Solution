import {PaymentAccount} from "./PaymentAccount";

export interface Client {
  id : number;
  firstName : string;
  lastName : string;
  email : string;
  phoneNumber : string;
  paymentAccount: PaymentAccount;
}
