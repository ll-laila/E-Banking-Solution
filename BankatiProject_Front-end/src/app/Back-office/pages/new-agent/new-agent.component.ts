import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import {IAgent} from "../../../models/Agent";

@Component({
  selector: 'app-agents',
  templateUrl: './new-agent.component.html',
  styleUrls: ['./new-agent.component.scss']
})
export class NewAgentComponent implements OnInit {
  agents: IAgent[] = [];
  constructor(private router: Router, private agentService: AgentService) { }

  ngOnInit(): void {
    this.getAllAgents();
  }


  getAllAgents(): void {

  }
  addAgent() {
    this.router.navigate(['/add-agent']);
  }

  deleteAgent(id: number) {

  }

  viewAgentDetails(id: number) {
    this.router.navigate(['/details-agent', id]);
  }
}
