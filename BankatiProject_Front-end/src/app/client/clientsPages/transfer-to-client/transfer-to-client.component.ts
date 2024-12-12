import { Component, OnInit } from '@angular/core';
import {TransactionServiceService} from "../../services/transaction-service.service";
import {Transaction} from "../../models/transaction";
import {TransactionType} from "../../models/transaction-type.enum";
import {Client} from "../../models/client";

@Component({
  selector: 'app-transfer-to-client',
  templateUrl: './transfer-to-client.component.html',
  styleUrls: ['./transfer-to-client.component.scss']
})
export class TransferToClientComponent implements OnInit {
  senderId: string = ''; // Peut être récupéré depuis une session utilisateur
  beneficiaryPhoneNumber: string = '';
  amount: number | null = null;
  senderPhoneNumber:string= '0667461703' ;
  devise:string ='';


  constructor(private transactionService: TransactionServiceService) { }

  ngOnInit(): void {
  }
  submitTransfer(): void {
    // Simuler la récupération de `beneficiaryId` via l'email
    const beneficiaryId = this.getBeneficiaryIdPhoneNumber(this.beneficiaryPhoneNumber);

    if (this.senderId && beneficiaryId && this.amount) {
      const transaction: Transaction = {
        senderId: this.senderId,
        beneficiaryId: beneficiaryId,
        amount: this.amount,
        transactionType: TransactionType.TRANSFER
      };

      this.transactionService.createTransaction(transaction).subscribe({
        next: (response) => {
          console.log('Transaction créée avec succès', response);
          alert('Le transfert a été effectué avec succès !');
        },
        error: (error) => {
          console.error('Erreur lors de la création de la transaction', error);
          alert('Une erreur est survenue. Veuillez réessayer.');
        }
      });
    } else {
      alert('Veuillez remplir tous les champs du formulaire.');
    }
  }

  private getBeneficiaryIdPhoneNumber(phone: string): string {
    return 'dummy-beneficiary-id'; // Remplacez ceci par une logique réelle
  }

  private getClientById(senderId: string): Client {
    return ;
  }
}
