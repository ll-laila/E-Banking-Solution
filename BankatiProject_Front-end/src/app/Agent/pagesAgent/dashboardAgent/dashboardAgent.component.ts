import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';


import {IClient} from '../../../models/Client';
import {ActivatedRoute, Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {SharedAgentService} from '../../../service/shared-agent.service';
import {IAgent} from '../../../models/Agent';
import {AgentService} from '../../../service/agent.service';
import {SharedInfosService} from '../../../service/shared-infos.service';
import {Subscription} from "rxjs";
import {UserResponse} from "../../../models/UserResponse";



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboardAgent.component.html',
  styleUrls: ['./dashboardAgent.component.scss']
})
export class DashboardAgentComponent implements OnInit {
  clients: UserResponse[] = [];
  phoneNumber: string;


  // tslint:disable-next-line:max-line-length
  constructor(private router: Router,  private agentService: AgentService,
              private sharedInfosService : SharedInfosService, private route: ActivatedRoute
  ) {
  }
  addClient() {
    this.router.navigate(['/add-clientt']);
  }



  agentId: string | null = null;


  ngOnInit(): void {
    this.agentId = localStorage.getItem('id'); // Récupérer l'agentId
    if (this.agentId) {
      this.getAllClientsByAgentId(this.agentId);
    } else {
      console.error('Aucun agentId trouvé.');
    }
  }

  getAllClientsByAgentId(agentId: string): void {
    this.agentService.getClientsByAgentId(agentId).subscribe(
      (clients) => {
        this.clients = clients;
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des clients :', error);
      }
    );
  }

  /*getAllClients(): void {
     this.agentService.getAllClients().subscribe(

      (clients) => {
        this.clients = clients; // Assigner les données reçues à this.clients
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des clients :', error);
      }
    );
  }


   */
  /*getAllClientByAgentId(): void {
    this.agentService.getAllClients().subscribe(res => {
      this.clients = res;
    }, error => {
      console.log(error);
    });

  }


  deleteClient(id: number) {
    console.log("i am id delete",id);
    this.agentService.deleteClient(id).subscribe(
      () => {
        console.log('Client deleted successfully.');
        this.getAllClients();
        //this.getAllClientByAgentId();
      },
      (error) => {
        console.error('An error occurred while deleting the client:', error);
      }
    );
    window.location.reload();
  }
  viewDetails(id: number) {
    console.log("i am id delete",id);
    this.agentService.viewDetailsClient(id).subscribe(
      () => {
        console.log('Client deleted successfully.');
        this.getAllClients();
        //this.getAllClientByAgentId();
      },
      (error) => {
        console.error('An error occurred while deleting the client:', error);
      }
    );
   // window.location.reload();
  }

   */



}

