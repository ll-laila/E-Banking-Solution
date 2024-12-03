import { Component, OnInit } from '@angular/core';
import {ClientComponentsModule} from "../Clientcomponents/ClientComponents.module";
import {RouterOutlet} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-admin-layout',
  templateUrl: './client-dash.component.html',
  standalone: true,
  imports: [
    ClientComponentsModule,
    RouterOutlet
  ],
  styleUrls: ['./client-dash.component.scss']
})
export class ClientDashComponent implements OnInit {

  isClient: boolean = false;

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
   /* this.isClient = this.authService.isClient();
    console.log('isClient:', this.isClient);*/
  }

}
