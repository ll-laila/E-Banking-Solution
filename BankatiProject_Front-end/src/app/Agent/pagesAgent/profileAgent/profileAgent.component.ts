import { Component, OnInit } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import {IAgent} from "../../../models/Agent";
import {SharedAgentService} from "../../../service/shared-agent.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './profileAgent.component.html',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  styleUrls: ['./profileAgent.component.scss']
})
export class ProfileAgentComponent implements OnInit {

  phoneNumber: string;
  agent: IAgent ;

  constructor(private sharedAgentService: SharedAgentService) { }


  ngOnInit(): void {
   this.agent = this.sharedAgentService.getAgent();
  }




}
