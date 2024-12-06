import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthLayoutRoutes } from './auth-layout.routing';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AgentchangePasswordComponent} from "./Agentchange-password/Agentchange-password.component";
import { ClientChangePasswordComponent } from './client-change-password/client-change-password.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AuthLayoutRoutes),
    FormsModule,
    LoginComponent,
  ],
  declarations: [
    RegisterComponent,
    AgentchangePasswordComponent,
    ClientChangePasswordComponent
  ]
})
export class AuthLayoutModule { }
