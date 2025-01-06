import { Component, OnInit } from '@angular/core';
import { ClientService } from "../../services/client.service";
import { TransactionResponse } from "../../models/transactionResponse";
import { Wallet } from "../../../models/wallet";
import { ClientRequest } from "../../models/clientRequest";

@Component({
  selector: 'app-budget-personel',
  templateUrl: './budget-personel.component.html',
  styleUrls: ['./budget-personel.component.scss']
})
export class BudgetPersonelComponent implements OnInit {

  wallet: Wallet | null = null;
  senderTransactions: TransactionResponse[] = [];
  beneficiaryTransactions: TransactionResponse[] = [];
  senderTotalAmount: number = 0;
  beneficiaryTotalAmount: number = 0;
  errorMessage: string | null = null;

  constructor(private clientService: ClientService) { }

  ngOnInit(): void {
    this.loadAllTransactions();
  }

  loadAllTransactions(): void {
    const userId = localStorage.getItem('id');
    if (userId) {
      this.clientService.getAllTransactionsByUserId(userId).subscribe(
        (data: TransactionResponse[]) => {
          // Filtrer les transactions pour l'expéditeur et le bénéficiaire
          this.senderTransactions = data.filter(transaction => transaction.senderId === userId);
          this.beneficiaryTransactions = data.filter(transaction => transaction.beneficiaryId === userId);

          // Calculer la somme des montants pour chaque liste
          this.senderTotalAmount = this.calculateTotalAmount(this.senderTransactions);
          this.beneficiaryTotalAmount = this.calculateTotalAmount(this.beneficiaryTransactions);
        },
        (error) => {
          this.errorMessage = 'Erreur lors de la récupération de toutes les transactions.';
          console.error('Error fetching all transactions:', error);
        }
      );
    }
  }

  // Fonction pour calculer la somme des montants
  calculateTotalAmount(transactions: TransactionResponse[]): number {
    return transactions.reduce((total, transaction) => total + transaction.amount, 0);
  }

}
