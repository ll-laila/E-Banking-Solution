import { Component, OnInit } from '@angular/core';
import {IClient} from '../../../models/Client';
import {ActivatedRoute, Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {AgentService} from "../../../service/agent.service";

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.scss']
})
export class EditClientComponent implements OnInit {


  public loading = false;
  public id: string | null = null;
  public client: IClient = {} as IClient;

  constructor(private activatedRoute: ActivatedRoute, private agentService: AgentService, private router: Router) {

  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((param) => {
      this.id = param.get('id');
    });
    if (this.id) {
      this.loading = true;
      // tslint:disable-next-line:radix
      this.agentService.getClient(parseInt(this.id)).subscribe((data) => {
        this.client = data;
        this.loading = false;
      }, (error) => {
        console.log(error);
      });
    }
  }



}
