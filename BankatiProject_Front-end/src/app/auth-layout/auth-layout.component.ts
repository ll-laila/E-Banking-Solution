import { Component, OnInit, OnDestroy } from '@angular/core';
import {Router, RouterLinkWithHref, RouterOutlet} from '@angular/router';
import {NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {DatePipe} from "@angular/common";
import {LoginComponent} from "./login/login.component";

@Component({
  selector: 'app-auth-layout',
  templateUrl: 'auth-layout.component.html',
  standalone: true,
  imports: [
    RouterLinkWithHref,
    NgbCollapseModule,
    RouterOutlet,
    DatePipe,
    LoginComponent
  ],
  styleUrls: ['auth-layout.component.scss']
})
export class AuthLayoutComponent implements OnInit, OnDestroy {
  date: Date = new Date();
  public isCollapsed = true;

  constructor(private router: Router) { }

  ngOnInit() {
    var html = document.getElementsByTagName("html")[0];
    html.classList.add("auth-layout");
    var body = document.getElementsByTagName("body")[0];
    body.classList.add("bg-default");
    this.router.events.subscribe((event) => {
      this.isCollapsed = true;
    });

  }
  ngOnDestroy() {
    var html = document.getElementsByTagName("html")[0];
    html.classList.remove("auth-layout");
    var body = document.getElementsByTagName("body")[0];
    body.classList.remove("bg-default");
  }
}
