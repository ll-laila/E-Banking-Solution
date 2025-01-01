import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarAdminComponent } from './sidebarAdmin/sidebarAdmin.component';
import { NavbarAdminComponent } from './navbarAdmin/navbarAdmin.component';
import { FooterAdminComponent } from './footerAdmin/footerAdmin.component';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AddAgentComponent } from './add-agent/add-agent.component';
import {FormsModule} from "@angular/forms";
import { DetailsAgentComponent } from './details-agent/details-agent.component';
import {EditAgentComponent} from "./edit-agent/edit-agent.component";
import { AddClientComponent } from './add-client/add-client.component';




@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    NgbModule,
    FormsModule
  ],
  declarations: [
    FooterAdminComponent,
    NavbarAdminComponent,
    SidebarAdminComponent,
    AddAgentComponent,
    EditAgentComponent,
    DetailsAgentComponent,
    AddClientComponent
  ],
  exports: [
    FooterAdminComponent,
    NavbarAdminComponent,
    SidebarAdminComponent
  ]
})
export class AdminComponentsModule { }
