import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../services/client.service';
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

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClientData();
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
