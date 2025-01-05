import { Component, OnInit } from '@angular/core';
import {IClient} from "../../../models/Client";
import {Router} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import {DatePipe} from "@angular/common";
import {AdminService} from "../../../service/admin.service";
import {IPaymentAccount} from "../../../models/paymentAccount";
import {IClientRegistrationRequest} from "../../../models/ClientRegistrationRequest";
import {ToastrService} from "ngx-toastr";
import {UserRequest} from "../../../models/UserRequest";

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  //styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {
  public client: UserRequest = {} as UserRequest;

  constructor(private agentService: AgentService, private router: Router , private toastr: ToastrService) {}

  ngOnInit(): void {
  }


  createClient() {
    /*this.agentService.createClient(this.client)
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
