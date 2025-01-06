import { Component, OnInit } from '@angular/core';
import { ClientRequest } from '../../models/clientRequest';
import {Wallet} from "../../../models/wallet";
import {SharedClientService} from "../../services/shared-client.service";
import {ClientService} from "../../services/client.service";
import {SharedInfosService} from "../../../service/shared-infos.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './profileClient.component.html',
  styleUrls: ['./profileClient.component.scss']
})
export class ProfileClientComponent implements OnInit {

  wallet: Wallet | null = null;
 client: ClientRequest = {
    id: '',
    agentId: '',
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    cin: '',
    birthDate: new Date(),
    phoneNumber: '',
    role: '',
    password: '',
    isFirstLogin: false,
    commercialRn: '',
    image: '',
    patentNumber: '',
    isPaymentAccountActivated: false,
    typeHissab: '',
    currency: '',
    token: ''
  };

  constructor(private clientService: ClientService) { }

  ngOnInit() {
    this.clientService.getUserById(localStorage.getItem('id')).subscribe((client) => {
      this.client = client;
    });
    this.clientService.getWalletByClientId(localStorage.getItem('id')).subscribe((wallet) => {
      this.wallet = wallet;
    });
  }

  

}
