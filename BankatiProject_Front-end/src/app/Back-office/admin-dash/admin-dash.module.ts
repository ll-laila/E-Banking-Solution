import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule} from '@angular/forms';

import { ClipboardModule } from 'ngx-clipboard';


import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {ProfileAdminComponent} from "../pages/profileAdmin/profileAdmin.component";
import {NewAgentComponent} from "../pages/new-agent/new-agent.component";
import {AdminDashRoutes} from "./admin-dash.routing";
import {ClientsComponent} from "../pages/clients/clients.component";
//import {NgxPaginationModule} from "ngx-pagination";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminDashRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule,
    //NgxPaginationModule

  ],
  declarations: [
    ProfileAdminComponent,
    NewAgentComponent,
    ClientsComponent,
  ]
})

export class AdminDashModule {}
