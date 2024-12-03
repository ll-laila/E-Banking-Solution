import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AgentservicesService} from '../../../service/agentservices.service';
import {IAgentServices} from '../../../models/AgentServices';

@Component({
  selector: 'app-edit-service',
  templateUrl: './edit-service.component.html',
  styleUrls: ['./edit-service.component.scss']
})
export class EditServiceComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private agentservices: AgentservicesService, private router: Router) {

  }

  public loading = false;
  public id: string | null = null;
  public agentservice: IAgentServices = {} as IAgentServices;


  ngOnInit(): void {

  }

  public update() {

  }


}
