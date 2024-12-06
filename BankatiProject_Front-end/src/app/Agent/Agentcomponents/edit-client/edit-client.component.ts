import { Component, OnInit } from '@angular/core';
import {IClient} from '../../../models/Client';
import {ActivatedRoute, Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.scss']
})
export class EditClientComponent implements OnInit {


  public loading = false;
  public id: string | null = null;
  public client: IClient = {} as IClient;

  constructor(private activatedRoute: ActivatedRoute, private clientService: ClientService, private router: Router) {

  }
  ngOnInit(): void {


  }







}
