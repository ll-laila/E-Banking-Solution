import { Component, OnInit } from '@angular/core';

import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {ToastrService} from 'ngx-toastr';
import {IClientRegistrationRequest} from '../../../models/ClientRegistrationRequest';
import {IAgent} from "../../../models/Agent";
import {IClient} from "../../../models/Client";

import {IPaymentAccount} from "../../../models/paymentAccount";


@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {
  public client: IClient = {} as IClient;
  public paymentAccount: IPaymentAccount={} as IPaymentAccount;

 clientRegistrationRequest: IClientRegistrationRequest = {} as IClientRegistrationRequest;
  constructor(private clientService: ClientService, private router: Router , private toastr: ToastrService) {}

  ngOnInit(): void {
  }

  createSubmit() {

  }

}
