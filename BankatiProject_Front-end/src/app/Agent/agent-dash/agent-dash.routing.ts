import { Routes } from '@angular/router';
import {DashboardAgentComponent} from '../pagesAgent/dashboardAgent/dashboardAgent.component';
import {ProfileAgentComponent} from '../pagesAgent/profileAgent/profileAgent.component';
import {AddClientComponent} from '../Agentcomponents/add-client/add-client.component';
import {ProfileAdminComponent} from '../../Back-office/pages/profileAdmin/profileAdmin.component';
import {NewAgentComponent} from '../../Back-office/pages/new-agent/new-agent.component';
import {AddAgentComponent} from '../../Back-office/Admincomponents/add-agent/add-agent.component';
import {EditAgentComponent} from '../../Back-office/Admincomponents/edit-agent/edit-agent.component';
import {DetailsAgentComponent} from '../../Back-office/Admincomponents/details-agent/details-agent.component';
import {DetailsClientComponent} from '../Agentcomponents/details-client/details-client.component';
import {EditClientComponent} from '../Agentcomponents/edit-client/edit-client.component';
import {ProfileClientComponent} from '../../client/clientsPages/profileClient/profileClient.component';
import {ServicesAgentComponent} from '../pagesAgent/services-agent/services-agent.component';
import {AddServiceComponent} from '../Agentcomponents/add-service/add-service.component';
import {TrasactionAgentComponent} from '../pagesAgent/trasaction-agent/trasaction-agent.component';
import {EditServiceComponent} from '../Agentcomponents/edit-service/edit-service.component';




export const AgentDashRoutes: Routes = [
  { path: 'agent',      component: DashboardAgentComponent },
  { path: 'agent-profile',   component: ProfileAgentComponent },
  {path: 'add-clientt',       component: AddClientComponent},
  {path: 'edit-service/:id',       component: EditServiceComponent},
  { path: 'edit-agent/:id', component: EditClientComponent },
  { path: 'details-agent/:id', component: DetailsClientComponent },
  {path: 'agent-service', component: ServicesAgentComponent},
  {path: 'add-service', component: AddServiceComponent},
  { path: 'edit-client/:id', component: EditClientComponent },
  { path: 'details-client/:id', component: DetailsClientComponent },
  { path: 'agent-transaction', component: TrasactionAgentComponent },

];
