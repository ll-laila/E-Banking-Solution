import { Component, OnInit } from '@angular/core';
import {TransactionServiceService} from "../../services/transaction-service.service";
import {Transaction} from "../../models/transaction";
import {TransactionType} from "../../models/transaction-type";
import {Client} from "../../models/client";
import {Router} from "@angular/router";

@Component({
  selector: 'app-transfer-to-client',
  templateUrl: './transfer-to-client.component.html',
  styleUrls: ['./transfer-to-client.component.scss']
})
export class TransferToClientComponent implements OnInit {
  senderId: string = '675d672698a04453154ddc2e'; // Peut être récupéré depuis une session utilisateur
  beneficiaryPhoneNumber: string = '';
  amount: number | null = null;
  senderPhoneNumber:string= '' ;
  devise:string ='';


  constructor(private transactionService: TransactionServiceService,private router :Router) { }

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
    if (!this.beneficiaryPhoneNumber || !this.amount) {
      alert('Veuillez remplir tous les champs du formulaire.');
      return;
    }

    this.transactionService.getClientIdByPhoneNumber(this.beneficiaryPhoneNumber).subscribe({
      next: (beneficiaryId) => {
        if (!beneficiaryId) {
          alert('Le numéro de téléphone ne correspond à aucun bénéficiaire.');
          return;
        }

        this.transactionService.createTransaction(this.senderId, beneficiaryId, this.amount, TransactionType.TRANSFER).subscribe({
          next: (response) => {
            console.log('Transaction créée avec succès:', response);
            alert(response); // Affiche le message texte retourné par le backend
            this.router.navigate(['/client/accueil']);
          },
          error: (error) => {
            console.error('Erreur lors de la création de la transaction:', error);
            alert(`Une erreur est survenue : ${error.message || 'Veuillez réessayer.'}`);
          }
        });
      },
      error: (error) => {
        console.error('Erreur lors de la récupération de l\'ID du bénéficiaire:', error);
        alert(`Impossible de récupérer l'ID du bénéficiaire: ${error.message || 'Veuillez réessayer.'}`);
      }
    });
  }



}
