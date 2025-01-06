import { Component, OnInit } from '@angular/core';
import {IClient} from '../../../models/Client';
import {ActivatedRoute, Router} from '@angular/router';
import {ClientService} from '../../../service/client.service';
import {AgentService} from "../../../service/agent.service";
import {UserResponse} from "../../../models/UserResponse";

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.scss']
})
export class EditClientComponent implements OnInit {


  public loading = false;
  public id: string | null = null;
  public client: UserResponse = {} as UserResponse;

  constructor(private activatedRoute: ActivatedRoute, private agentService: AgentService, private router: Router) {

  }
  ngOnInit(): void {
    this.loadClientData();
  }

  loadClientData(){
    this.activatedRoute.paramMap.subscribe((param) => {
      this.id = param.get('id');
    });
    if (this.id) {
      this.loading = true;
      // tslint:disable-next-line:radix
      this.agentService.getClient(this.id).subscribe((data) => {
        /*if (data.birthDate) {
          data.birthDate = this.formatDateForDisplay(data.birthDate);
        }
        if (data.createdDate) {
          data.createdDate = this.formatDateForDisplay(data.createdDate);
        }*/
        this.client = data;
        this.loading = false;
      }, (error) => {
        console.log(error);
      });
    }
  }

  formatDateForDisplay(date: string): string {
    const parsedDate = new Date(date);
    const day = String(parsedDate.getDate()).padStart(2, '0'); // Ajouter un zéro devant si nécessaire
    const month = String(parsedDate.getMonth() + 1).padStart(2, '0'); // Mois commence à 0, donc ajouter +1
    const year = parsedDate.getFullYear();

    return `${day}-${month}-${year}`;
  }

  // Fonction de formatage pour le champ input[type="date"] "yyyy-mm-dd"
  formatDateForInput(date: string): string {
    const parsedDate = new Date(date);
    const year = parsedDate.getFullYear();
    const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
    const day = String(parsedDate.getDate()).padStart(2, '0');

    return `${year}-${month}-${day}`;
  }


 /* updateClient() {
    this.activatedRoute.paramMap.subscribe((param) => {
      this.id = param.get('id');
    });
    if (this.id) {
      this.loading = true;
      this.agentService.updateClient(this.id, this.client).subscribe((data) => {

        this.client = data;
        this.loading = false;
        this.router.navigate([`/agent`]).then();

      }, (error) => {
        console.log(error);
      });

    }
  }

  */
}
