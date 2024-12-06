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


@Component({
  selector: 'app-payment',
  templateUrl: './payment.html',
  styleUrls: ['./payment.scss']
})
export class Payment implements OnInit {

  currentStep = 1;
  public paymentForm1: FormGroup;

  public client: Client;
  public agent: Agent;
  public service: ServiceAgent;
  public donationAmount: number;
  public refOp: string;

  constructor(private route: ActivatedRoute,
              private fb: FormBuilder,
              private router: Router,
              private paymentService: PaymentService,
              private sharedClientService: SharedClientService,
              private sharedAgentServiceService: SharedAgentServiceService,
              private sharedAgentService: SharedAgentService,
   ) {

    this.paymentForm1 = this.fb.group({
      donationAmount: [0 , [Validators.required, Validators.min(1)]]
    });
  }


  ngOnInit() {
    this.client = this.sharedClientService.getClient();
    this.agent = this.sharedAgentService.getAgent();
    this.service = this.sharedAgentServiceService.getServiceAgent();
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
    const paymentDetails: PaymentDetails = {
      refOperation: this.refOp,
      idClient: this.client.id,
      idCreditor: this.agent.id,
      idService: this.service.id,
      amount: this.donationAmount
    };

    console.log(paymentDetails);

    this.paymentService.PayService(paymentDetails).subscribe(
      response => {
        this.router.navigate(['/client/agents'],  {queryParams: { status: response.status, responseMessage:  response.message}});
      },
      error => {
        this.router.navigate(['/client/agents'], {queryParams: {  status: 0, responseMessage:  "Paiement échoué"}});
      }
    );

  }

  annuler() {
    this.router.navigate(['/client/accueil']);
  }


  generateReferenceOperation(): string {
    let reference = '';
    for (let i = 0; i < 12; i++) {
      reference += Math.floor(Math.random() * 10).toString();
    }
    return reference;
  }

}

