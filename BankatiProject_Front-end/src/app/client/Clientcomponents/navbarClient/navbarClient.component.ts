import { Component, OnInit, ElementRef } from '@angular/core';
import { ROUTES } from '../sidebarClient/sidebarClient.component';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FeedDetails} from "../../models/feedDetails";
import {PaymentService} from "../../services/payment.service";
import {Client} from "../../models/client";
import {SharedClientService} from "../../services/shared-client.service";
import {FeedResponse} from "../../models/feedResponse";

@Component({
  selector: 'app-navbar-client',
  templateUrl: './navbarClient.component.html',
  styleUrls: ['./navbarClient.component.scss']
})
export class NavbarClientComponent implements OnInit {

  public client: Client;
  public paymentForm: FormGroup;
  public focus;
  public listTitles: any[];
  public location: Location;

  public feedDetails: FeedDetails = {
    idClient : -1,
    amount: 0
  };

  public responseMessage: string;
  public currentStep: number;

  constructor(location: Location,
              private element: ElementRef,
              private router: Router,
              private fb: FormBuilder,
              private paymentService: PaymentService,
              private sharedClientService: SharedClientService
  ) {
    this.location = location;
    this.paymentForm = this.fb.group({
      montant: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit() {
    this.listTitles = ROUTES.filter(listTitle => listTitle);
  }

  submitForm() {
    if (this.paymentForm.valid) {
      this.client = this.sharedClientService.getClient();
      this.feedDetails.idClient = this.client.id;
      this.feedDetails.amount = this.paymentForm.get('montant')?.value;
      console.log(this.feedDetails);

      this.paymentService.feedPaymentAccount(this.feedDetails).subscribe((response: FeedResponse) => {
        this.responseMessage = response.message;
        this.currentStep = 2;
        this.router.navigate(['/client']);

      }, error => {
        console.error('Alimentation échouée:', error);
      });

    } else {
      console.log('Formulaire non valide');
    }
  }


  logout() {
    this.router.navigate(['/login']);
  }

  getTitle() {
    let titlee = this.location.prepareExternalUrl(this.location.path());
    if (titlee.charAt(0) === '#') {
      titlee = titlee.slice(1);
    }

    for (let item = 0; item < this.listTitles.length; item++) {
      if (this.listTitles[item].path === titlee) {
        titlee = this.listTitles[item].title;
      }
    }

    if (titlee.toLowerCase().includes('paiement') ) {
      titlee = 'paiement';
    }

    if (titlee.toLowerCase().includes('agents') ) {
      titlee = 'agents';
    }

    return titlee;
  }




}
