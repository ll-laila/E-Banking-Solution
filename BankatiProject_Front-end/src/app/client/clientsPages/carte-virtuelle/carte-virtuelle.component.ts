import { Component, OnInit } from '@angular/core';
import { VirtualCard } from "../../../models/VirtualCard";
import { VirtualCardService } from "../../services/virtualCard.service";
import { ActivatedRoute } from "@angular/router";
import { ClientService } from "../../services/client.service";
import { ClientRequest } from "../../models/clientRequest";
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-carte-virtuelle',
  templateUrl: './carte-virtuelle.component.html',
  styleUrls: ['./carte-virtuelle.component.scss']
})
export class CarteVirtuelleComponent implements OnInit {

  public paymentForm: FormGroup; // Déclarer le formulaire
  public currentStep: number;
  userId: string = ''; // L'ID utilisateur que tu veux utiliser
  card: VirtualCard = {
    id: '',
    cardNumber: '#### #### #### ####',
    userId: '',
    expirationDate: '',
    status: 'INACTIVE', // Valeur par défaut pour un état neutre
    createdAt: '',
    updatedAt: '',
    montant: '0', // Montant vide initialisé à 0
  };

  client: ClientRequest = {
    id: '',
    agentId: '',
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    cin: '',
    birthDate: new Date(),
    phoneNumber: '',
    role: '',
    password: '',
    isFirstLogin: false,
    commercialRn: '',
    image: '',
    patentNumber: '',
    isPaymentAccountActivated: false,
    typeHissab: '',
    currency: '',
    token: ''
  };

  newCardRef: string = ''; // Référence de la nouvelle carte

  constructor(private fb: FormBuilder, private clientService: ClientService, private route: ActivatedRoute, private virtualCardService: VirtualCardService) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('id');

    this.paymentForm = this.fb.group({
      montant: [0, Validators.required] // Initialisation du formulaire avec un champ montant
    });

    this.getCards();



    //this.extractMonthYear(this.card.expirationDate);
  }

  // Récupérer les cartes pour l'utilisateur spécifié
  getCards(): void {
    this.clientService.getUserById(localStorage.getItem('id')).subscribe((client) => {
      this.client = client;
    });
    this.virtualCardService.getCardsByUser(localStorage.getItem('id')).subscribe(
      (card: VirtualCard) => {
        this.card = card;
        console.log("dataaaa",this.card);
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
        this.card = card; // Ajoute la nouvelle carte à la liste
        console.log('Carte créée:', card);
      },
      (error) => {
        console.error('Erreur lors de la création de la carte:', error);
      }
    );
  }

  feedAmount: string ;
  public responseMessage: string;

  // Alimenter la carte
  feedCard(): void {
    // Vérifier si le formulaire est valide avant de récupérer la valeur
    if (this.paymentForm.valid) {
      this.feedAmount = this.paymentForm.get('montant')?.value; // Récupérer la valeur du montant

      this.virtualCardService.feedCard(localStorage.getItem('id'), this.feedAmount).subscribe(
        (result: VirtualCard) => {
          this.virtualCardService.getCardsByUser(localStorage.getItem('id')).subscribe(
            (card: VirtualCard) => {
              this.card = card; // Mettre à jour la carte après le paiement
              console.log('Carte alimentée:', card);
            },
            (error) => {
              console.error('Erreur lors de la recharge des données du client:', error);
            }
          );
        },
        (error) => {
          console.error('Erreur lors de l\'alimentation de la carte:', error);
        }
      );
    } else {
      console.log('Veuillez entrer un montant valide.');
    }
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

  month: string;
  year: string;

  // Extraire le mois et l'année de la date d'expiration
  extractMonthYear(expirationDate: string): void {
    const date = new Date(expirationDate); // Convertir la date JSON en objet Date
    this.month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois sont indexés de 0
    this.year = date.getFullYear().toString();
    console.log("Mois:", this.month);
  }
}
