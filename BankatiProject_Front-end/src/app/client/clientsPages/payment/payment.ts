import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Agent } from "../../models/agent";
import { ServiceAgent } from "../../models/serviceAgent";
import { Router } from '@angular/router';
import {Client} from "../../models/client";
import {PaymentDetails} from "../../models/payment";
import {PaymentService} from "../../services/payment.service";
import {SharedClientService} from "../../services/shared-client.service";
import {SharedAgentServiceService} from "../../services/shared-agent-service.service";
import {SharedAgentService} from "../../services/shared-agent.service";
import {Transaction} from "../../models/transaction";
import {TransactionServiceService} from "../../services/transaction-service.service";
import {TransactionType} from "../../models/transaction-type";
import {SharedInfosService} from "../../../service/shared-infos.service";
import {AgentService} from "../../../service/agent.service";
import {AgentRequest} from "../../../models/AgentRequest";
import {UserResponse} from "../../../models/UserResponse";


@Component({
  selector: 'app-payment',
  templateUrl: './payment.html',
  styleUrls: ['./payment.scss']
})
export class Payment implements OnInit {

  currentStep = 1;
  public paymentForm1: FormGroup;

  public client: UserResponse;
  public agent: Agent;
  public service: ServiceAgent;
  public donationAmount: number;
  public refOp: string;
  public agentId:string;
  public agentName: string;
  public serviceName: string;
  public serviceType:string;
  public agentImage:string;

  constructor(private route: ActivatedRoute,
              private fb: FormBuilder,
              private router: Router,
              private paymentService: PaymentService,
              private sharedClientService: SharedClientService,
              private sharedAgentServiceService: SharedAgentServiceService,
              private agentService:AgentService,
              private transactionService: TransactionServiceService,
              private sharedInfosService: SharedInfosService
   ) {

    this.paymentForm1 = this.fb.group({
      donationAmount: [0 , [Validators.required, Validators.min(1)]],
      subscription: [false]
    });
  }

  senderId: string ;


  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.agentName = params['agentName'];
      this.serviceName = params['serviceName'];
      this.serviceType=params['serviceType'];
      this.agentImage=params['agentImage'];
    });
    this.senderId=this.sharedInfosService.getId();
    this.agentId = this.sharedInfosService.getAgentId();
    this.service = this.sharedAgentServiceService.getServiceAgent();

    this.transactionService.getClientInfos(this.senderId).subscribe({
      next: (response) => {
        this.client = response;
        console.log('Client Info:', this.client);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des infos client:', err);
      }
    });
  }

  onCheckboxChange(event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;

    if (isChecked) {
      this.activateSubscription();
    }
  }

  activateSubscription() {
    if (!this.donationAmount || this.donationAmount <= 0) {
      alert('Le montant de l\'abonnement est invalide.');
      return;
    }

    this.paymentService.createSubscriptionTransaction(this.senderId, this.agent.id, this.donationAmount).subscribe({
      next: () => {
        console.log('Abonnement activé avec succès.');
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'abonnement : ', err);
        alert(`Erreur : ${err}`);
      }
    });
  }



  validate1() {
    if (this.paymentForm1.valid) {
      this.donationAmount = this.paymentForm1.get('donationAmount')?.value;
      this.currentStep = 2;
    }
  }

  onCancel() {
    this.paymentForm1.reset();
  }


  validate2() {
    this.currentStep = 3;
    this.refOp = this.generateReferenceOperation();
  }


  validate3() {
        this.transactionService.createTransaction(this.senderId, this.agentId, this.donationAmount, TransactionType.PAYMENT).subscribe({
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

  }




  annuler() {
    this.router.navigate(['/client/accueil']);
  }


  generateReferenceOperation(): string {
    const timestamp = Date.now().toString().slice(-5); // Les 5 derniers chiffres de l'heure actuelle
    let reference = '';
    for (let i = 0; i < 7; i++) {
      reference += Math.floor(Math.random() * 10).toString();
    }
    return `REF${timestamp}${reference}`;
  }

}

