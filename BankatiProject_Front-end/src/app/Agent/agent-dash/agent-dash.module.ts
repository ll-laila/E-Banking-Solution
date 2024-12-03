import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ClipboardModule } from 'ngx-clipboard';


import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import {AgentDashRoutes} from './agent-dash.routing';
import {DashboardAgentComponent} from "../pagesAgent/dashboardAgent/dashboardAgent.component";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AgentDashRoutes),
    FormsModule,
    HttpClientModule,
    NgbModule,
    ClipboardModule
  ],
  declarations: [
    DashboardAgentComponent,
  ]
})

export class AgentDashModule {}
