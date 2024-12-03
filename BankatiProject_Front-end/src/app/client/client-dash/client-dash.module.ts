import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ClipboardModule } from 'ngx-clipboard';

import {ClientDashRoutes} from './client-dash.routing';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {DashboardClientComponent} from "../clientsPages/dashboardClient/dashboardClient.component";
import {ProfileClientComponent} from "../clientsPages/profileClient/profileClient.component";
import {HistoryComponent} from "../clientsPages/history/history.component";
import {CreditorsListComponent} from "../clientsPages/creditorsList/creditorsList.component";
import {Payment} from "../clientsPages/payment/payment";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ClientDashRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule,
    ReactiveFormsModule
  ],
  declarations: [
    DashboardClientComponent,
    ProfileClientComponent,
    HistoryComponent,
    CreditorsListComponent,
    Payment
  ]
})

export class ClientDashModule {}
