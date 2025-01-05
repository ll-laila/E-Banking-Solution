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

  public loading = false;
  public id: string | null = null;
  public agentServices: IAgentServices = {} as IAgentServices;
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
      this.agentService.getService(this.id).subscribe((data) => {
        this.agentServices = data;
        this.loading = false;
      }, (error) => {
        console.log(error);
      });
    }
  }

  public update() {
    if (this.id) {
      this.agentService.updateService(this.agentServices,this.id).subscribe((data) => {
        this.router.navigate([`/agent`]).then();
      }, (error) => {
        this.router.navigate(['/edit-client/:id']).then();
      });
    }

  }




}
