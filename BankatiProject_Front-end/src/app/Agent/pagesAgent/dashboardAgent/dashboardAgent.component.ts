import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';


import {IClient} from '../../../models/Client';
import {ActivatedRoute, Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {SharedAgentService} from '../../../service/shared-agent.service';
import {IAgent} from '../../../models/Agent';
import {AgentService} from '../../../service/agent.service';
import {SharedInfosService} from '../../../service/shared-infos.service';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboardAgent.component.html',
  styleUrls: ['./dashboardAgent.component.scss']
})
export class DashboardAgentComponent implements OnInit {
  clients: IClient[] = [];
  phoneNumber: string;
  agent: IAgent;

  // tslint:disable-next-line:max-line-length
  constructor(private router: Router, private clientService: ClientService, private agentService: AgentService, private sharedAgentService: SharedAgentService,
              private sharedInfosService : SharedInfosService, private route: ActivatedRoute
  ) {
  }


  ngOnInit(): void {
    this.getAgentByPhone(this.sharedInfosService.getPhoneNumber());
    this.agent = this.sharedAgentService.getAgent();
    //this.getAllClients(this.agent.id);
    //this.getAllClientByAgentId(this.agent.id);
    //this.sharedAgentService.setAgent(this.agent);
  }


  getAgentByPhone(phoneNum: string) {

  }


  addClient() {
    this.router.navigate(['/add-client']);
  }

  getAllClients(idAgent: number): void {

  }

  getAllClientByAgentId(idAgent: number): void {


  }


  deleteClient(id: number) {

    window.location.reload(); }


    }

