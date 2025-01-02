import { Component, OnInit } from '@angular/core';
import {catchError, throwError} from 'rxjs';
import {Router} from '@angular/router';
import {IAgentServices} from '../../../models/AgentServices';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IClient} from "../../../models/Client";
import {IAgent} from "../../../models/Agent";
import {AgentService} from "../../../service/agent.service";
import {SharedInfosService} from "../../../service/shared-infos.service";

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.scss']
})
export class AddServiceComponent implements OnInit { public service: IAgentServices = {} as IAgentServices;
  clients: IClient[] = [];
  agent: IAgent;
  constructor(private agentService: AgentService, private router: Router,private sharedInfosService:SharedInfosService) {}

  async ngOnInit(): Promise<void> {
    try {
      const phoneNumber = this.sharedInfosService.getPhoneNumber();
      //this.agent = await this.getAgentByPhone(phoneNumber);
    } catch (error) {
      console.error('Error retrieving agent:', error);
    }
  }

  /*
  async getAgentByPhone(phoneNum: string): Promise<IAgent> {
    try {
      const agent = await this.agentService.getAgentByPhoneNumber(phoneNum).toPromise();
      console.log(agent);
      return agent;
    } catch (error) {
      console.error('Error retrieving agent by phone number:', error);
      throw error;
    }
  }

   */

  createService() {
    this.agentService.createService(this.service,123)
      .pipe(
        catchError(error => {
          console.error('Erreur lors de la création du service:', error);
          return throwError(error); // Renvoyer l'erreur pour la traiter en aval si nécessaire
        })
      )
      .subscribe((data: any) => {
        console.log(this.service);
        console.log(data);
        console.log('service créé avec succès');
        this.router.navigate(['/agent-service'])
      });
  }


}
