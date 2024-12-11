import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule  } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import {AdminDashComponent} from './Back-office/admin-dash/admin-dash.component';

import {AgentDashComponent} from './Agent/agent-dash/agent-dash.component';
import { ClientDashComponent } from './client/client-dash/client-dash.component';
import {LoginComponent} from './auth-layout/login/login.component';
import {AdminGuard} from './guards/admin.guard';
import {AgentGuard} from './guards/agent.guard';
import {ClientGuard} from './guards/client.guard';
import {AgentchangePasswordComponent} from './auth-layout/Agentchange-password/Agentchange-password.component';
import {ClientChangePasswordComponent} from './auth-layout/client-change-password/client-change-password.component';


const routes: Routes = [
   {
    path: '',
    component: AuthLayoutComponent,
  },

  {
    path: 'admin',
    redirectTo: 'admin',
    canActivate: [AdminGuard],
    pathMatch: 'full',
  }, {
    path: '',
    component: AdminDashComponent,
    children: [
      {
        path: '',
        loadChildren: () => import('src/app/Back-office/admin-dash/admin-dash.module').then(m => m.AdminDashModule)
      }
    ]
  },
  {

    path: 'agent',
    redirectTo: 'agent',
    canActivate: [AgentGuard],
    pathMatch: 'full',
  }, {
    path: '',
    component: AgentDashComponent,
    children: [
    {
      path: '',
      loadChildren: () => import('src/app/Agent/agent-dash/agent-dash.module').then(m => m.AgentDashModule)
    }]},

  {
  path: 'client',
  redirectTo: 'client',
    canActivate: [ClientGuard],
  pathMatch: 'full',

}, {
    path: 'client',
    component : ClientDashComponent,
    children : [
      {
        path: '',
        loadChildren: () => import('src/app/client/client-dash/client-dash.module').then(m => m.ClientDashModule)
      }
    ]
  },   {


    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'agent-change-password',
    component: AgentchangePasswordComponent,
  },
  {
    path: 'client-change-password',
    component: ClientChangePasswordComponent,
  }
  ,

];


@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true
    })
  ],
  exports: [
  ],
})
  export class AppRoutingModule {}
