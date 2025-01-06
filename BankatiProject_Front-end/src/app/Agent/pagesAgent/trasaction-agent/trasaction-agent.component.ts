import { Component, OnInit } from '@angular/core';
import { TransactionResponse } from '../../../client/models/transactionResponse';
import {ClientService} from '../../../client/services/client.service'


@Component({
  selector: 'app-trasaction-agent',
  templateUrl: './trasaction-agent.component.html',
  styleUrls: ['./trasaction-agent.component.scss']
})
export class TrasactionAgentComponent implements OnInit {


  public operations: TransactionResponse[];
  errorMessage: string | null = null;


  constructor(private clientService: ClientService) {}

  ngOnInit() {

    this.clientService.getAllTransactionsByUserId(localStorage.getItem('id')).subscribe(
          (data: TransactionResponse[]) => {
            this.operations = data;
          },
          (error) => {
            this.errorMessage = 'Erreur lors de la récupération de toutes les transactions.';
            console.error('Error fetching all user transactions:', error);
          }
        );
  }







}
