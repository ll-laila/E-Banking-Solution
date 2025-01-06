import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
import { TransactionResponse } from '../../models/transactionResponse';
import {SharedClientService} from "../../services/shared-client.service";
import {Client} from "../../models/client";

@Component({
  selector: 'app-tables',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {

  public client: Client;
  transactions: TransactionResponse[] = [];
  errorMessage: string | null = null;


  constructor(private clientService: ClientService) {}

  ngOnInit() {

    this.clientService.getAllTransactionsByUserId(localStorage.getItem('id')).subscribe(
      (data: TransactionResponse[]) => {
        this.transactions = data;
      },
      (error) => {
        this.errorMessage = 'Erreur lors de la récupération de toutes les transactions.';
        console.error('Error fetching all user transactions:', error);
      }
    );

  }





}
