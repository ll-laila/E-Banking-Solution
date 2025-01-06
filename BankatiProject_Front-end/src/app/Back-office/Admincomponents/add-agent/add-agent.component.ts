import { Component, OnInit } from '@angular/core';
import {AgentRequest} from '../../../models/AgentRequest';
import {AgentService} from '../../../service/agent.service';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';
import {DatePipe} from "@angular/common";
import {ToastrService} from "ngx-toastr";
import {AdminService} from "../../../service/admin.service";

@Component({
  selector: 'app-add-agent',
  templateUrl: 'add-agent.component.html',
  styleUrls: ['add-agent.component.scss'],
  providers: [DatePipe]
})
export class AddAgentComponent implements OnInit {
 // public agent: AgentRequest = {} as AgentRequest;
 public agent: AgentRequest = {
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


  constructor(private adminService: AdminService, private router: Router, private toastr: ToastrService) {}

  ngOnInit(): void {
  }


  createSubmit(){


  }


  generatePassword(): string {
    let password = '';
    for (let i = 0; i < 6; i++) {
      password += Math.floor(Math.random() * 10).toString();
    }
    return password;
  }

  addAgent(): void {
    if (this.agent.firstName && this.agent.lastName && this.agent.email) {

      this.agent.role = "AGENT";
      this.agent.isFirstLogin = true;
      this.agent.password = this.generatePassword();

      this.adminService.addAgent(this.agent).subscribe(
        (data: any) => {
          this.toastr.success('Agent ajouté avec succès', 'Succès');
          this.router.navigate(['/admin']).then();
        },
        (error) => {
          this.toastr.error('Erreur lors de l\'ajout de l\'agent', 'Erreur');
          setTimeout(() => {
            this.router.navigate(['/admin']).then();
          }, 300); // Délai de 3 secondes avant la redirection
        }
      );
    } else {
      this.toastr.warning('Veuillez remplir tous les champs', 'Attention');
    }
  }

}
