import { Routes } from '@angular/router';

import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AgentchangePasswordComponent} from "./Agentchange-password/Agentchange-password.component";
import {ClientChangePasswordComponent} from "./client-change-password/client-change-password.component";

export const AuthLayoutRoutes: Routes = [
    { path: 'login',          component: LoginComponent },
    { path: 'register',       component: RegisterComponent },
    { path: 'agent-change-password', component: AgentchangePasswordComponent},
    { path: 'client-change-password', component: ClientChangePasswordComponent}
];
