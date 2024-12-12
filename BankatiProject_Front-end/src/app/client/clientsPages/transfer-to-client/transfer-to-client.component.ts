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
  senderId: string = '67599d391bd1ad55c1af27e6'; // Peut être récupéré depuis une session utilisateur
  beneficiaryPhoneNumber: string = '';
  amount: number | null = null;
  senderPhoneNumber:string= '' ;
  devise:string ='';


  constructor(private transactionService: TransactionServiceService) { }

  ngOnInit(): void {
    // Récupérer les informations du sender via son ID
    this.transactionService.getClientInfo(this.senderId).subscribe({
      next: (sender: Client) => {
        this.senderPhoneNumber = sender.phoneNumber;
        this.devise = sender.currency;
        console.log('Infos du sender récupérées avec succès :', sender);
        console.log('Devise récupérée :', this.devise);
      },
      error: (error) => {
        console.error('Erreur lors de la récupération des infos du sender :', error);
        alert('Impossible de récupérer les informations de l\'expéditeur.');
      }
    });
  }

  submitTransfer(): void {
    if (!this.beneficiaryPhoneNumber || !this.amount ) {
      alert('Veuillez remplir tous les champs du formulaire.');
      return;
    }

    // Récupérer l'ID du bénéficiaire par son numéro de téléphone
    this.transactionService.getClientIdByPhoneNumber(this.beneficiaryPhoneNumber).subscribe({
      next: (beneficiaryId) => {
        const transaction: Transaction = {
          senderId: this.senderId,
          beneficiaryId: beneficiaryId,
          amount: this.amount!,
          transactionType: TransactionType.TRANSFER
        };

        // Créer la transaction
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
      },
      error: (error) => {
        console.error('Erreur lors de la récupération de l\'ID du bénéficiaire', error);
        alert('Impossible de récupérer l\'ID du bénéficiaire.');
      }
    });
  }


}
