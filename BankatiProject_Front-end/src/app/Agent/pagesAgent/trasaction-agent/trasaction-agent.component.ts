import { Component, OnInit } from '@angular/core';
import {Operation} from "../../../client/models/operation";
import {ClientService} from "../../../service/client.service";
import {SharedAgentService} from "../../../service/shared-agent.service";
import {IAgent} from "../../../models/Agent";
import {AgentService} from "../../../service/agent.service";

@Component({
  selector: 'app-trasaction-agent',
  templateUrl: './trasaction-agent.component.html',
  styleUrls: ['./trasaction-agent.component.scss']
})
export class TrasactionAgentComponent implements OnInit {

  public agent: IAgent;
  public operations: Operation[];
  public solde : number;


  constructor(private agentService: AgentService,private sharedAgentService:SharedAgentService) {}

  ngOnInit() {
    this.agent = this.sharedAgentService.getAgent();
    this.getAgentOperations(this.agent.id);
  }


  public getAgentOperations(idAgent: number) {
    this.agentService.getAgentOperation(idAgent).subscribe(res => {
      console.log(res);
      this.operations = res;
    }, error => {
      console.log(error);
    });
  }

  public getAgentSolde(idAgent: string){
    this.agentService.getAgentSolde(idAgent).subscribe(res  => {
      this.solde = res;
    },error  => {
      console.log(error);
    });

}


}
