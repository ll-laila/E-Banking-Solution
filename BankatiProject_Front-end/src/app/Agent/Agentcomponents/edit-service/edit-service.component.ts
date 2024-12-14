import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IAgentServices} from '../../../models/AgentServices';
import {AgentService} from "../../../service/agent.service";

@Component({
  selector: 'app-edit-service',
  templateUrl: './edit-service.component.html',
  styleUrls: ['./edit-service.component.scss']
})
export class EditServiceComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private agentservice: AgentService, private router: Router) {

  }

  public loading = false;
  public id: string | null = null;
  public agentServices: IAgentServices = {} as IAgentServices;


  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.id = params['id'];
    });

    if (this.id) {
      this.loading = true;
      // tslint:disable-next-line:radix
      this.agentservice.getService(parseInt(this.id)).subscribe((data) => {
        this.agentServices = data;
        this.loading = false;
      }, (error) => {
        console.log(error);
      });
    }
  }

  public update() {
    if (this.id) {
      // tslint:disable-next-line:radix
      this.agentservice.updateService(this.agentServices, parseInt(this.id)).subscribe((data) => {
        this.router.navigate([`/agent`]).then();
      }, (error) => {
        this.router.navigate(['/edit-client/:id']).then();
      });
    }

  }


}
