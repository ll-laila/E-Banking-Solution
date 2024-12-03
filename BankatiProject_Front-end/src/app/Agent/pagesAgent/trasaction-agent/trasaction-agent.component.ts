import { Component, OnInit } from '@angular/core';
import {Operation} from "../../../client/models/operation";
import {ClientService} from "../../../service/client.service";
import {SharedAgentService} from "../../../service/shared-agent.service";
import {IAgent} from "../../../models/Agent";

@Component({
  selector: 'app-trasaction-agent',
  templateUrl: './trasaction-agent.component.html',
  styleUrls: ['./trasaction-agent.component.scss']
})
export class TrasactionAgentComponent implements OnInit {

  public agent: IAgent;
  public operations: Operation[];

  constructor(private clientService: ClientService,private sharedAgentService:SharedAgentService) {}

  ngOnInit() {
    this.agent = this.sharedAgentService.getAgent();
    this.getAgentOperations(this.agent.id);
  }


  public getAgentOperations(idAgent: number) {

  }


}
