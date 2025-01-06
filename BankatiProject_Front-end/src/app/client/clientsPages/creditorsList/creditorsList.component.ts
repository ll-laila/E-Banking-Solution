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
import {AgentAndServices} from "../../../models/AgentAndServices";
import {AgentDashComponent} from "../../../Agent/agent-dash/agent-dash.component";

@Component({
  selector: 'app-icons',
  templateUrl: './creditorsList.component.html',
  styleUrls: ['./creditorsList.component.scss']
})
export class CreditorsListComponent implements OnInit {
  public agentandservices:AgentAndServices[]=[];
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
          this.getAllServicesByAgent(agent);
        });
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
      }
    );
  }


  getAllServicesByAgent(agent:AgentRequest): void {
    this.agentService.getAgentServices(agent.id).subscribe(
      (services: IAgentServices[]) => {
        const agentAndServices: AgentAndServices = {
          agent: agent,
          services: services
        };

        // Ajouter cet objet à la liste
        this.agentandservices.push(agentAndServices);
        console.log(this.agentandservices);
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
