import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {IClient} from '../../../models/Client';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IAgentServices} from '../../../models/AgentServices';
import {IAgent} from '../../../models/Agent';
import {SharedAgentService} from '../../../service/shared-agent.service';
import {AgentService} from "../../../service/agent.service";
import {SharedInfosService} from "../../../service/shared-infos.service";

@Component({
  selector: 'app-services-agent',
  templateUrl: './services-agent.component.html',
  styleUrls: ['./services-agent.component.scss']
})
export class ServicesAgentComponent implements OnInit {

  constructor(private router: Router, private agentservices: AgentService, private sharedInfosService: SharedInfosService) { }

  services: IAgentServices[] = [];
  agentId: string;


  ngOnInit(): void {
    this.agentId = localStorage.getItem('id'); // Récupérer l'agentId
    if (this.agentId) {
      this.getAllServicesByAgent(this.agentId);
    } else {
      console.error('Aucun agentId trouvé.');
    }

  }

  addService() {
    this.router.navigate(['/add-service']);
  }


  getAllServicesByAgent(agentId:string): void {
    this.agentservices.getAllAgentServices(agentId).subscribe(
      (services: IAgentServices[]) => {
        this.services = services;
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des agents :', error);
      }
    );
  }



  deleteService(id: string): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce service ?')) {
      this.agentservices.deleteService(id).subscribe({
        next: (response) => {
          console.log('Service supprimé avec succès:', response.message);
          alert('Service supprimé avec succès.');
        },
        error: (error) => {
          console.error('Erreur lors de la suppression du service:', error);
          alert('Une erreur est survenue lors de la suppression du service.');
        }
      });
    }
  }


}
