import { Component, OnInit } from '@angular/core';

import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {ToastrService} from 'ngx-toastr';
import {IClientRegistrationRequest} from '../../../models/ClientRegistrationRequest';
import {IAgent} from "../../../models/Agent";
import {IClient} from "../../../models/Client";

import {IPaymentAccount} from "../../../models/paymentAccount";
import {AgentService} from "../../../service/agent.service";


@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {
  public client: IClient = {} as IClient;
  public paymentAccount: IPaymentAccount={} as IPaymentAccount;

 clientRegistrationRequest: IClientRegistrationRequest = {} as IClientRegistrationRequest;
  constructor(private agentService: AgentService, private router: Router , private toastr: ToastrService) {}

  ngOnInit(): void {
  }


  createClient() {
   /* this.agentService.createClient(this.client)
      .subscribe((data: any) => {
          this.toastr.success('Client created successfully', 'Success');
          this.router.navigate([`/agent`]).then();
        },
        (error) => {
          this.toastr.error('Error creating client', 'Error');
          setTimeout(() => {
            this.router.navigate([`/agent`]).then();
          }, 300); // Délai de 3 secondes avant la redirection
        });*/
  }

}
