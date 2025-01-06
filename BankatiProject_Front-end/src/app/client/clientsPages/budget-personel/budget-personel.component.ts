import { Component, OnInit } from '@angular/core';
import { ClientService } from "../../services/client.service";
import { TransactionResponse } from "../../models/transactionResponse";
import { Wallet } from "../../../models/wallet";
import { ClientRequest } from "../../models/clientRequest";
import { DepenseRequest } from '../../../models/DepenseRequest';
import { DepenseResponse } from '../../../models/DepenseResponse';

@Component({
  selector: 'app-budget-personel',
  templateUrl: './budget-personel.component.html',
  styleUrls: ['./budget-personel.component.scss']
})
export class BudgetPersonelComponent implements OnInit {
  depenses: DepenseResponse[] = [];
  newDepense: DepenseRequest = { userId: '', userPhone: '', montant: 0 };
  userId: string = ''; // Initialisé après récupération de l'ID
  loading: boolean = false;
  error: string | null = null;

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

  loadClientData(): void {
    const storedId = localStorage.getItem('id');
    if (storedId) {
      this.userId = storedId;
      this.newDepense.userId = this.userId;
      this.fetchDepenses(); // Charger les dépenses uniquement si l'ID est défini
    } else {
      this.error = 'ID utilisateur non trouvé dans le stockage local.';
    }
  }

  fetchDepenses(): void {
    if (!this.userId) {
      this.error = 'Impossible de charger les dépenses sans ID utilisateur.';
      return;
    }
    this.loading = true;
    this.clientService.getAllDepensesByUser(this.userId).subscribe({
      next: (data) => {
        this.depenses = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des dépenses.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  createDepense(): void {
    if (!this.newDepense.montant || this.newDepense.montant <= 0) {
      this.error = 'Veuillez entrer un montant valide.';
      return;
    }
    this.newDepense.userId = this.userId;
    this.loading = true;
    this.clientService.createDepense(this.newDepense).subscribe({
      next: (data) => {
        this.depenses.push(data);
        this.newDepense = { userId: localStorage.getItem('id'), userPhone: '', montant: 0 };
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors de la création de la dépense.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  updateDepense(depenseId: string, montant: number): void {
    if (montant <= 0) {
      this.error = 'Le montant doit être supérieur à zéro.';
      return;
    }
    this.loading = true;
    this.clientService.updateDepense(depenseId, montant).subscribe({
      next: () => {
        this.fetchDepenses();
      },
      error: (err) => {
        this.error = 'Erreur lors de la mise à jour de la dépense.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  cancelDepense(depenseId: string): void {
    this.loading = true;
    this.clientService.cancelDepense(depenseId).subscribe({
      next: () => {
        this.depenses = this.depenses.filter(depense => depense.id !== depenseId);
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors de l\'annulation de la dépense.';
        console.error(err);
        this.loading = false;
      }
    });
  }
}
