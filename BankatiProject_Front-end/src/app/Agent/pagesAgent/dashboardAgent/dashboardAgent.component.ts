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



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboardAgent.component.html',
  styleUrls: ['./dashboardAgent.component.scss']
})
export class DashboardAgentComponent implements OnInit {
  clients: any[] = [];
  phoneNumber: string;
  agent: IAgent;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router,  private agentService: AgentService,
              private sharedInfosService : SharedInfosService, private route: ActivatedRoute
  ) {
  }


  async ngOnInit(): Promise<void> {
    this.getAllClients();
    /*
    try {
      const phoneNumber = this.sharedInfosService.getPhoneNumber();
      this.agent = await this.getAgentByPhone(phoneNumber);
      if (this.agent) {
        this.getAllClients(this.agent.id);
      }
    } catch (error) {
      console.error('Error retrieving agent:', error);
    }

     */
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



  getAllClients(): void {
     this.agentService.getAllClients().subscribe(

      (clients) => {
        this.clients = clients; // Assigner les données reçues à this.clients
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des clients :', error);
      }
    );
  }

  getAllClientByAgentId(): void {
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



}

