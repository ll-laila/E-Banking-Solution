import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";

import {MyToken} from "../../models/MyToken";
import jwtDecode from "jwt-decode";
import {AgentChangePasswordService} from "../../service/AgentChangePassword.service";

@Component({
  selector: 'app-change-password',
  templateUrl: './Agentchange-password.component.html',
  styleUrls: ['./Agentchange-password.component.scss']
})
export class AgentchangePasswordComponent implements OnInit {
  test: Date = new Date();
  loading = false;
  submitted = false;

  constructor(
    private router: Router,
    private userService: AgentChangePasswordService,
    private cookieService: CookieService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const token = this.cookieService.get('token');
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
    });
  }

  changePasswordForm = new FormGroup({
    password: new FormControl('', Validators.required)
  });

  onSubmit(changePswForm: NgForm) {

  }

  get f() { return this.changePasswordForm.controls; }
}
