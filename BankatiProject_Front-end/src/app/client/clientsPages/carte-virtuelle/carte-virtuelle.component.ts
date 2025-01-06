import { Component, OnInit } from '@angular/core';
import {VirtualCard} from "../../../models/VirtualCard";
import {VirtualCardService} from "../../services/virtualCard.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-carte-virtuelle',
  templateUrl: './carte-virtuelle.component.html',
  styleUrls: ['./carte-virtuelle.component.scss']
})
export class CarteVirtuelleComponent implements OnInit {

  userId: string = '';  // L'ID utilisateur que tu veux utiliser
  card: VirtualCard ;
  newCardRef: string = ''; // Référence de la nouvelle carte

  constructor(private route: ActivatedRoute,private virtualCardService: VirtualCardService) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('id');

    // Appeler cette méthode pour charger les cartes dès que le composant est initialisé
    if (this.userId) {
      this.getCards();
    }
  }

  // Récupérer les cartes pour l'utilisateur spécifié
  getCards(): void {
    this.virtualCardService.getCardsByUser(this.userId).subscribe(
      (card: VirtualCard) => {
        this.card = card;
      },
      (error) => {
        console.error('Erreur lors de la récupération des cartes:', error);
      }
    );
  }

  // Créer une nouvelle carte
  createCard(): void {
    this.virtualCardService.createCard(this.userId).subscribe(
      (card: VirtualCard) => {
        this.card = card  // Ajoute la nouvelle carte à la liste
        console.log('Carte créée:', card);
      },
      (error) => {
        console.error('Erreur lors de la création de la carte:', error);
      }
    );
  }

  // Activer une carte
  activateCard(cardId: string): void {
    this.virtualCardService.activateCard(cardId).subscribe(
      (card: VirtualCard) => {
        console.log('Carte activée:', card);
        this.getCards(); // Rafraîchir la liste des cartes
      },
      (error) => {
        console.error('Erreur lors de l\'activation de la carte:', error);
      }
    );
  }

  // Désactiver une carte
  deactivateCard(cardId: string): void {
    this.virtualCardService.deactivateCard(cardId).subscribe(
      (card: VirtualCard) => {
        console.log('Carte désactivée:', card);
        this.getCards(); // Rafraîchir la liste des cartes
      },
      (error) => {
        console.error('Erreur lors de la désactivation de la carte:', error);
      }
    );
  }
}

