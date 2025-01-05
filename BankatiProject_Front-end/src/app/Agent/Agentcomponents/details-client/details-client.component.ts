import { Component, OnInit } from '@angular/core';
import {IClient} from "../../../models/Client";
import {ActivatedRoute, Router} from "@angular/router";
import {ClientService} from "../../../service/client.service";
import {AgentService} from "../../../service/agent.service";
import {AgentRequest} from "../../../models/AgentRequest";
import {UserResponse} from "../../../models/UserResponse";

@Component({
  selector: 'app-details-client',
  templateUrl: './details-client.component.html',
  styleUrls: ['./details-client.component.scss']
})
export class DetailsClientComponent implements OnInit {

  public loading = false;
  public id: string | null = null;
  public client: UserResponse = {} as UserResponse;

  constructor(private activatedRoute: ActivatedRoute, private agentService: AgentService, private router: Router) {

  }
  ngOnInit(): void {
    this.loadClientData();
  }
  loadClientData(){
    this.activatedRoute.paramMap.subscribe((param) => {
      this.id = param.get('id');
    });
    if (this.id) {
      this.loading = true;
      // tslint:disable-next-line:radix
      this.agentService.getClient(this.id).subscribe((data) => {
        this.client = data;
        this.loading = false;
      }, (error) => {
        console.log(error);
      });
    }
  }
}
