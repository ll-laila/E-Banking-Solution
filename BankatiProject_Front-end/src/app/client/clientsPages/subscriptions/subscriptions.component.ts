import { Component, OnInit } from '@angular/core';
import { TransactionServiceService } from "../../services/transaction-service.service";
import { SharedInfosService } from "../../../service/shared-infos.service";
import {AgentRequest} from "../../../models/AgentRequest";
import {UserResponse} from "../../../models/UserResponse";

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})
export class SubscriptionsComponent implements OnInit {
  public agentId: string = '';
  public clientId: string = '';
  public agent:UserResponse ;
  public client:UserResponse;
  public subscriptions: any[] = []; // Initialisation de la liste des abonnements
  public errorMessage: string = ''; // Message d'erreur à afficher en cas d'échec

  constructor(
    private subscriptionService: TransactionServiceService,
    private sharedInfoService: SharedInfosService
  ) {}

  ngOnInit(): void {
    // Récupérer l'ID du client depuis SharedInfosService
    this.clientId= localStorage.getItem('id');
    this.agentId =  localStorage.getItem('agentId');
    this.subscriptionService.getClientInfos(this.clientId).subscribe({
      next: (response) => {
        this.client = response;
        console.log('Client Info:', this.client);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des infos client:', err);
      }
    });
    this.subscriptionService.getClientInfos(this.agentId).subscribe({
      next: (response) => {
        this.agent = response;
        console.log('Client Info:', this.agent);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des infos client:', err);
      }
    });
    // Appeler le service pour récupérer les abonnements
    this.subscriptionService.getUserSubscriptions(this.clientId).subscribe(
      (data) => {
        this.subscriptions = data;
        console.log('Abonnements récupérés:', this.subscriptions);
      },
      (error) => {
        console.error('Erreur lors de la récupération des abonnements:', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des abonnements.';
      }
    );
  }
}
