import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client';
import { Wallet } from '../../models/wallet';
import { Transaction } from '../../models/transaction';

@Component({
  selector: 'app-dashboard-client',
  templateUrl: './dashboardClient.component.html',
  styleUrls: ['./dashboardClient.component.scss'],
})
export class DashboardClientComponent implements OnInit {
  client: Client | null = null; // Initialisation
  wallet: Wallet | null = null; // Initialisation
  transactions: Transaction[] = [];
  errorMessage: string | null = null; // Initialisation

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    const clientId = '67596dcc2500d851f75a6f98';
    this.loadClientData(clientId);
    this.loadAllTransactions(clientId);
  }

  loadClientData(clientId: string): void {
    this.clientService.getClientById(clientId).subscribe(
      (data: Client) => {
        this.client = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des données du client.';
        console.error('Error fetching client data:', error);
      }
    );

    this.clientService.getWalletByClientId(clientId).subscribe(
      (data: Wallet) => {
        this.wallet = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des données du portefeuille.';
        console.error('Error fetching wallet data:', error);
      }
    );
  }
  loadTransactions(userId: string): void {
    this.clientService.getTransactionsByUserId(userId).subscribe(
      (data: Transaction[]) => {
        this.transactions = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération des transactions.';
        console.error('Error fetching transactions:', error);
      }
    );
  }
  loadAllTransactions(userId: string): void {
    this.clientService.getAllTransactionsByUserId(userId).subscribe(
      (data: Transaction[]) => {
        this.transactions = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération de toutes les transactions.';
        console.error('Error fetching all transactions:', error);
      }
    );
  }
}



