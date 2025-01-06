import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client';
import { SharedInfosService } from '../../../service/shared-infos.service';
import { ClientRequest } from '../../models/clientRequest';
import { TransactionResponse } from '../../models/transactionResponse';
import {Wallet} from "../../../models/wallet";

@Component({ 
  selector: 'app-dashboard-client',
  templateUrl: './dashboardClient.component.html',
  styleUrls: ['./dashboardClient.component.scss'],
})
export class DashboardClientComponent implements OnInit {
  wallet: Wallet | null = null; // Initialisation
  transactions: TransactionResponse[] = [];
  errorMessage: string | null = null; // Initialisation


 client: ClientRequest = {
    id: '',
    agentId: '',
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    cin: '',
    birthDate: new Date(),
    phoneNumber: '',
    role: '',
    password: '',
    isFirstLogin: false,
    commercialRn: '',
    image: '',
    patentNumber: '',
    isPaymentAccountActivated: false,
    typeHissab: '',
    currency: '',
    token: ''
  };


  constructor(private clientService: ClientService,private sharedInfosService: SharedInfosService) {}

  ngOnInit(): void {
    this.loadClientData();
    this.loadAllTransactions();
  } 

  loadClientData(): void {
    this.clientService.getUserById(localStorage.getItem('id')).subscribe((client) => {
      this.client = client;
    });
    this.clientService.getWalletByClientId(localStorage.getItem('id')).subscribe((wallet) => {
      this.wallet = wallet;
    },
    (error) => {
      this.errorMessage = 'Erreur lors de la récupération des données du portefeuille.';
      console.error('Error fetching wallet data:', error);
    });
  }



  loadAllTransactions(): void {
    this.clientService.getAllTransactionsByUserId(localStorage.getItem('id')).subscribe(
      (data: TransactionResponse[]) => {
        this.transactions = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération de toutes les transactions.';
        console.error('Error fetching all transactions:', error);
      }
    );
  }
}



