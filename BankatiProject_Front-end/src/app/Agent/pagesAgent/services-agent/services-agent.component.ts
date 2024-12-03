import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {IClient} from '../../../models/Client';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IAgentServices} from '../../../models/AgentServices';
import {IAgent} from '../../../models/Agent';
import {SharedAgentService} from '../../../service/shared-agent.service';

@Component({
  selector: 'app-services-agent',
  templateUrl: './services-agent.component.html',
  styleUrls: ['./services-agent.component.scss']
})
export class ServicesAgentComponent implements OnInit {

  constructor(private router: Router, private agentservices: AgentservicesService, private sharedAgentService: SharedAgentService) { }

  services: IAgentServices[] = [];
  agent: IAgent ;


  ngOnInit(): void {
    this.agent = this.sharedAgentService.getAgent();
    this.getAllServicesByAgent(this.agent.id);
  }

  addService() {
    this.router.navigate(['/add-service']);
  }


  getAllServicesByAgent(idAgent: number): void {


  }



  deleteService(id: number) {
    console.log(id);
  }




  updateService(id: number) {
    this.router.navigate(['/edit-agent'], { queryParams: { id: id } });
  }

}
