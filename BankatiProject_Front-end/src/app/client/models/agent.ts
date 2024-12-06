import {ServiceAgent} from "./serviceAgent";

export interface Agent {
  id? : number;
  firstName : string;
  lastName : string;
  email : string;
  phoneNumber : string;
  image : string;
  services: ServiceAgent[];
}
