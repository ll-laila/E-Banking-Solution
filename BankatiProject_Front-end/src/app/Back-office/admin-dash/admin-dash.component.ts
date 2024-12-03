import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-dash.component.html',
  styleUrls: ['./admin-dash.component.scss']
})
export class AdminDashComponent implements OnInit {
  isAdmin: boolean = false;

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {

  }


}
