import { Routes } from '@angular/router';
import {ProfileAdminComponent} from "../pages/profileAdmin/profileAdmin.component";
import {NewAgentComponent} from "../pages/new-agent/new-agent.component";
import {AddAgentComponent} from "../Admincomponents/add-agent/add-agent.component";
import {DetailsAgentComponent} from "../Admincomponents/details-agent/details-agent.component";
import {ClientsComponent} from "../pages/clients/clients.component";
import {AddClientComponent} from "../Admincomponents/add-client/add-client.component";




export const AdminDashRoutes: Routes = [

    { path: 'user-profile',   component: ProfileAdminComponent },
    { path: 'admin',         component: NewAgentComponent },
    { path: 'add-agent', component: AddAgentComponent },
    { path: 'details-agent/:id', component: DetailsAgentComponent },
    { path: 'clients', component: ClientsComponent },
  { path: 'add-client', component: AddClientComponent },

];
