import { Component, OnInit } from '@angular/core';
import { ClientService } from "../../services/client.service";
import { Agent } from "../../models/agent";
import { ServiceAgent } from "../../models/serviceAgent";
import {ActivatedRoute, Router} from '@angular/router';
import {SharedAgentService} from "../../services/shared-agent.service";
import {SharedAgentServiceService} from "../../services/shared-agent-service.service";
import {PaymentService} from "../../services/payment.service";
import {AgentService} from "../../../service/agent.service";
import {IAgentServices} from "../../../models/AgentServices";
import {AdminService} from "../../../service/admin.service";
import {AgentRequest} from "../../../models/AgentRequest";
import {UserRequest} from "../../../models/UserRequest";

@Component({
  selector: 'app-icons',
  templateUrl: './creditorsList.component.html',
  styleUrls: ['./creditorsList.component.scss']
})
export class CreditorsListComponent implements OnInit {

  public copy: string;
  public agents: AgentRequest[] = []; // Initialisation vide
  public responseMessage: string;
  public currentMsg: number;
  public agentservices: IAgentServices[] = []; // Initialisation vide

  constructor(
    private agentService: AgentService,
    private adminService: AdminService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.copy = "";
    this.getAllAgents();
    this.route.queryParams.subscribe(params => {
      if (params['responseMessage'] != null) {
        switch (+params['status']) {
          case 1:
            this.currentMsg = 1;
            break;
          case 0:
            this.currentMsg = 0;
            break;
        }
        this.responseMessage = params['responseMessage'];
      }
    });
  }

  getAllAgents(): void {

    this.adminService.getAllAgents().subscribe(
      (agents) => {

        this.agents = agents; // Assigner les agents reçus
        console.log(this.agents);
        this.agents.forEach(agent => {
          this.getAllServicesByAgent(agent.id);
        });
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
      }
    );
  }

  getAllServicesByAgent(agentId:string): void {
    this.agentService.getAgentServices(agentId).subscribe(
      (services: IAgentServices[]) => {
        this.agentservices = services;
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
      }
    );
  }



  redirectToPayment(agent: AgentRequest, service: IAgentServices) {
    this.router.navigate(['/client/paiement'], {
      queryParams: {
        agentId: agent.id,
        agentImage:agent.image,
        serviceId: service.id,
        agentName: `${agent.firstName} ${agent.lastName}`,
        serviceName: service.name,
        serviceType:service.type,

      }
    });
  }

}
