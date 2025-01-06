import { Component, OnInit } from '@angular/core';
import {ClientService} from "../../services/client.service";
import {TransactionResponse} from "../../models/transactionResponse";
import {Wallet} from "../../../models/wallet";
import {ClientRequest} from "../../models/clientRequest";

@Component({
  selector: 'app-budget-personel',
  templateUrl: './budget-personel.component.html',
  styleUrls: ['./budget-personel.component.scss']
})
export class BudgetPersonelComponent implements OnInit {

  wallet: Wallet | null = null;
  transactions: TransactionResponse[] = [];
  errorMessage: string | null = null;




  constructor(private  clientService: ClientService) { }

  ngOnInit(): void {
    this.loadAllTransactions();
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
