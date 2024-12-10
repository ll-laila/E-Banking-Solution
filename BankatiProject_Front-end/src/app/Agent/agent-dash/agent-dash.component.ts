import { Component, OnInit } from '@angular/core';
import {AgentComponentsModule} from "../Agentcomponents/AgentComponents.module";
import {ActivatedRoute, Router, RouterOutlet} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";
import {IClient} from "../../models/Client";
import {IAgent} from "../../models/Agent";
import {ClientService} from "../../service/client.service";
import {AgentService} from "../../service/agent.service";
import {SharedInfosService} from "../../service/shared-infos.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-admin-layout',
  templateUrl: './agent-dash.component.html',
  standalone: true,
  imports: [
    AgentComponentsModule,
    RouterOutlet
  ],
  styleUrls: ['./agent-dash.component.scss']
})
export class AgentDashComponent implements OnInit{

  public agent: IAgent = {} as IAgent;

  constructor(private agentService: AgentService, private router: Router, private datePipe: DatePipe) {}

  ngOnInit(): void {
  }





}
