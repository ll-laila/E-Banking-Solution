import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

declare interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}
export const ROUTES: RouteInfo[] = [
    { path: '/admin', title: 'Agents', icon: 'fas fa-users text-red', class: '' },
    { path: '/clients', title: 'Clients',  icon:'ni-single-02 text-yellow', class: '' },
    { path: '/user-profile', title: 'Profile',  icon:'ni-single-02 text-yellow', class: '' },
    { path: '', title: 'Logout', icon: 'fas fa-sign-out-alt text-danger', class: ''},
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebarAdmin.component.html',
  styleUrls: ['./sidebarAdmin.component.scss']
})
export class SidebarAdminComponent implements OnInit {

  public menuItems: any[];
  public isCollapsed = true;

  constructor(private router: Router) { }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    this.router.events.subscribe((event) => {
      this.isCollapsed = true;
   });
  }

  logout() {
    this.router.navigate(['/login']);
  }
}
