import { Component, OnInit } from '@angular/core';

import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {ToastrService} from 'ngx-toastr';
import {IClientRegistrationRequest} from '../../../models/ClientRegistrationRequest';
import {IAgent} from "../../../models/Agent";
import {IClient} from "../../../models/Client";

import {IPaymentAccount} from "../../../models/paymentAccount";
import {AgentService} from "../../../service/agent.service";
import {AgentRequest} from "../../../models/AgentRequest";
import {UserRequest} from "../../../models/UserRequest";
import {SharedInfosService} from "../../../service/shared-infos.service";


@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.scss']
})
export class AddClientComponent implements OnInit {

  public client: AgentRequest = {
    id: null,
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

  constructor(private agentService: AgentService, private router: Router , private toastr: ToastrService,private sharedInfosService:SharedInfosService) {}

  ngOnInit(): void {
  }

  generatePassword(): string {
    let password = '';
    for (let i = 0; i < 6; i++) {
      password += Math.floor(Math.random() * 10).toString();
    }
    return password;
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

  addClient(): void {
    if (this.client.firstName && this.client.lastName && this.client.email) {

      this.client.role = "CLIENT";
      this.client.agentId =this.sharedInfosService.getId()
      this.client.isFirstLogin = true;
      this.client.password = this.generatePassword();

      this.agentService.createClient(this.client).subscribe(
        (data: any) => {
          this.toastr.success('client ajouté avec succès', 'Succès');
          this.router.navigate(['/agent']).then();
        },
        (error) => {
          this.toastr.error('Erreur lors de l\'ajout de l\'agent', 'Erreur');
          setTimeout(() => {
            this.router.navigate(['/agent']).then();
          }, 300); // Délai de 3 secondes avant la redirection
        }
      );
    } else {
      this.toastr.warning('Veuillez remplir tous les champs', 'Attention');
    }
  }


}
