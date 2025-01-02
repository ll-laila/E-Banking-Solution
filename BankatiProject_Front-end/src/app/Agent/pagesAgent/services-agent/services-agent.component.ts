import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {IClient} from '../../../models/Client';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IAgentServices} from '../../../models/AgentServices';
import {IAgent} from '../../../models/Agent';
import {SharedAgentService} from '../../../service/shared-agent.service';
import {AgentService} from "../../../service/agent.service";

@Component({
  selector: 'app-services-agent',
  templateUrl: './services-agent.component.html',
  styleUrls: ['./services-agent.component.scss']
})
export class ServicesAgentComponent implements OnInit {

  constructor(private router: Router, private agentservices: AgentService, private sharedAgentService: SharedAgentService) { }

  services: IAgentServices[] = [];
  agent: IAgent ;


  ngOnInit(): void {
    this.agent = this.sharedAgentService.getAgent();
    this.getAllServicesByAgent();
  }

  addService() {
    this.router.navigate(['/add-service']);
  }


  getAllServicesByAgent(): void {
    this.agentservices.getAllAgentServices().subscribe(
      (services: IAgentServices[]) => {
        this.services = services;
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
      }
    );

  }



  deleteService(id: number) {
    console.log(id);
  }




  updateService(id: number) {
    this.router.navigate(['/edit-agent'], { queryParams: { id: id } });
  }

}
