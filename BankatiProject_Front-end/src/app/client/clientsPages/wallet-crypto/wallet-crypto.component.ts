import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-wallet-crypto',
  templateUrl: './wallet-crypto.component.html',
  styleUrls: ['./wallet-crypto.component.scss']
})
export class WalletCryptoComponent implements OnInit {

  isClicked = true;
  allCryptos = false;
  transferCryptos = false;
  selleCryptos = false;
  isModalOpen = false;

  constructor() { }

  ngOnInit(): void {

  }

  onDivClick1(): void {
    this.isClicked = false;
    this.selleCryptos = true;
    console.log('Div clicked, isClicked:', this.isClicked);
  }

  onDivClick2(): void {
    this.isClicked = false;
    this.allCryptos = true;
    console.log('Div clicked, isClicked:', this.isClicked);
  }

  onDivClick3(): void {
    this.isClicked = false;
    this.transferCryptos = true;
    console.log('Div clicked, isClicked:', this.isClicked);
  }



  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  acheterCrypto() {
    console.log('Cryptomonnaie achetée');
    this.closeModal();
  }



  vendreSubmit() {

  }

  transferSubmit() {

  }
}
