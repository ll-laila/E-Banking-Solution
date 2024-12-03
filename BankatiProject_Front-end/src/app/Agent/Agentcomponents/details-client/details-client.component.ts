import { Component, OnInit } from '@angular/core';
import {IClient} from "../../../models/Client";
import {ActivatedRoute} from "@angular/router";
import {ClientService} from "../../../service/client.service";

@Component({
  selector: 'app-details-client',
  templateUrl: './details-client.component.html',
  styleUrls: ['./details-client.component.scss']
})
export class DetailsClientComponent implements OnInit {

  client: IClient;

  constructor(private route: ActivatedRoute, private clientService: ClientService) { }

  ngOnInit(): void {

  }

}
