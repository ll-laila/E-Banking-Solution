import { Component, OnInit } from '@angular/core';
import { Client } from "../../models/client";
import { ClientService } from "../../services/client.service";
import { Operation } from '../../models/operation';
import { ActivatedRoute } from "@angular/router";
import { SharedClientService } from "../../services/shared-client.service";
import { PaymentAccount } from "../../models/paymentAccount";
import { SharedInfosService } from "../../../service/shared-infos.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboardClient.component.html',
  styleUrls: ['./dashboardClient.component.scss']
})

export class DashboardClientComponent implements OnInit {

  public phoneNumber: string | undefined;

  public client: Client;

  public operations: Operation[];

  constructor(
    private route: ActivatedRoute,
    private clientService: ClientService,
    private sharedClientService: SharedClientService,
    private sharedInfosService: SharedInfosService
  ) { }

  ngOnInit() {

  }


}
