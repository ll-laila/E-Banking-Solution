import { Component, OnInit } from '@angular/core';
import {IAgent} from "../../../models/Agent";
import {ActivatedRoute, Router} from "@angular/router";
import {AgentService} from "../../../service/agent.service";

@Component({
  selector: 'app-edit-agent',
  templateUrl: './edit-agent.component.html',
  styleUrls: ['./edit-agent.component.scss']
})
export class EditAgentComponent implements OnInit {

  public loading: boolean =false;
  public id: string | null = null;
  public agent:IAgent = {} as IAgent;

  constructor(private activatedRoute : ActivatedRoute, private agentService : AgentService, private router : Router){

  }
  ngOnInit(): void {

  }


  public update(){

  }

}
