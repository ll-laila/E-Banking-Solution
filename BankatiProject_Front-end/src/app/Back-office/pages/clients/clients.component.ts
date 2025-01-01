import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AdminService} from "../../../service/admin.service";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss']
})
export class ClientsComponent implements OnInit {
  clients: any[] = [];
  constructor(private router: Router, private adminService: AdminService) { }

  ngOnInit(): void {
    this.getAllClients()
  }

  getAllClients(): void {
    this.adminService.getAllClients().subscribe(

      (clients) => {
        this.clients = clients; // Assigner les données reçues à this.clients
      },
      (error) => {
        console.error('Une erreur s\'est produite lors de la récupération des clients :', error);
      }
    );
  }

  addClient() {
    this.router.navigate(['/add-client']);
  }

  deleteClient(id: string) {
    console.log("i am id delete",id);
    this.adminService.deleteUser(id).subscribe(
      () => {
        console.log('Client deleted successfully.');
        this.getAllClients();
        //this.getAllClientByAgentId();
      },
      (error) => {
        console.error('An error occurred while deleting the client:', error);
      }
    );
    window.location.reload();
  }

  viewClientDetails(id: string) {

  }
}
