import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: 'accueil', title: 'Accueil',  icon: 'ni-tv-2 text-primary', class: '' },
  { path: 'agents', title: 'Payer un service',  icon: 'fas fa-users text-red', class: '' },
  { path: 'transferToClient', title: 'Transfer d\'argent ',  icon: 'fas fa-users text-red', class: '' },
  { path: 'historique', title: 'Historique',  icon: 'fas fa-chart-bar text-blue', class: '' },
  { path: 'My-Subscriptions', title: 'My Subscriptions',  icon: 'fas fa-chart-bar text-blue', class: '' },
  { path: 'carte-virtuelle', title: 'Carte virtuelle',  icon: 'ni-single-02 text-yellow', class: '' },
  { path: 'crypto', title: 'Portefeuille cryptos',  icon: 'ni-single-02 text-yellow', class: '' },
  { path: 'buget-personnel', title: 'Gestion des dépenses',  icon: 'ni-single-02 text-yellow', class: '' },
  { path: 'mon-compte', title: 'Mon compte',  icon: 'ni-single-02 text-yellow', class: '' },
  { path: '', title: 'Logout', icon: 'fas fa-sign-out-alt text-danger', class: ''},
];

@Component({
  selector: 'app-sidebar-client',
  templateUrl: './sidebarClient.component.html',
  styleUrls: ['./sidebarClient.component.scss']
})
export class SidebarClientComponent implements OnInit {

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
