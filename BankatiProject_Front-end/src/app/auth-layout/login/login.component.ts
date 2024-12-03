import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MyToken } from 'src/app/models/MyToken';

import { DatePipe } from '@angular/common';

import { AuthenticationService } from '../../service/authentication.service';
import jwtDecode from "jwt-decode";
import { SharedInfosService } from "../../service/shared-infos.service";

@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  standalone: true,
  imports: [
    FormsModule,
    DatePipe
  ],
  styleUrls: ['login.component.scss']
})
export class LoginComponent implements OnInit {

  public phoneNumber: string;
  test: Date = new Date();

  constructor(
    private router: Router,
    private userService: AuthenticationService,
    private sharedInfosService: SharedInfosService
  ) {
  }

  ngOnInit(): void {
  }


}
