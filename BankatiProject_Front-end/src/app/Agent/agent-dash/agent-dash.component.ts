import { Component, OnInit } from '@angular/core';
import {AgentComponentsModule} from "../Agentcomponents/AgentComponents.module";
import {RouterOutlet} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";

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
export class AgentDashComponent implements OnInit {

  //isAgent: boolean = false;

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
   /* this.isAgent = this.authService.isAgent();
    console.log('isAgent:', this.isAgent);*/
  }

}
