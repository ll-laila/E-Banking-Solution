import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarAgentComponent } from './sidebarAgent/sidebarAgent.component';
import { NavbarAgentComponent } from './navbarAgent/navbarAgent.component';
import { FooterAgentComponent } from './footerAgent/footerAgent.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AddClientComponent } from './add-client/add-client.component';
import {FormsModule} from '@angular/forms';
import { EditClientComponent } from './edit-client/edit-client.component';
import { DetailsClientComponent } from './details-client/details-client.component';
import { AddServiceComponent } from './add-service/add-service.component';
import { EditServiceComponent } from './edit-service/edit-service.component';
import { UpdateServiceComponent } from './update-service/update-service.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        NgbModule,
        FormsModule
    ],
  declarations: [
    FooterAgentComponent,
    NavbarAgentComponent,
    SidebarAgentComponent,
    AddClientComponent,
    EditClientComponent,
    DetailsClientComponent,
    AddServiceComponent,
    EditServiceComponent,
    UpdateServiceComponent
  ],
  exports: [
    FooterAgentComponent,
    NavbarAgentComponent,
    SidebarAgentComponent
  ]
})
export class AgentComponentsModule { }
