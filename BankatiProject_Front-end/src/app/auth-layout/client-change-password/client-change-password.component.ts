import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {ClientChangePasswordService} from "../../service/client-change-password.service";

@Component({
  selector: 'app-client-change-password',
  templateUrl: 'client-change-password.component.html',
  styleUrls: ['client-change-password.component.scss']
})
export class ClientChangePasswordComponent implements OnInit {
  test: Date = new Date();
  loading = false;
  submitted = false;

  constructor(
    private router: Router,
    private userService: ClientChangePasswordService,
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
