import { Component, OnInit } from '@angular/core';
import {WalletCryptoService} from '../../services/wallet-crypto.service';
import {TransactionCrypto} from '../../models/crypto/transaction-crypto';
import {WalletCrypto} from '../../models/crypto/wallet-crypto';


@Component({
  selector: 'app-wallet-crypto',
  templateUrl: './wallet-crypto.component.html',
  styleUrls: ['./wallet-crypto.component.scss']
})
export class WalletCryptoComponent implements OnInit {

  idUser = '77597a1b0c8a5706c60d2dd0';
  isClicked = true;
  allCryptos = false;
  transferCryptos = false;
  selleCryptos = false;
  isModalOpen = false;
  walletCrypto: WalletCrypto | null = null;
  transactions: TransactionCrypto[] = [];
  errorMessage = '';
  constructor(private walletCryptoService: WalletCryptoService) { }

  ngOnInit(): void {
    this.loadUserWalletCrypto(this.idUser);
    this.loadUserTransactions(this.idUser);
  }



  loadUserWalletCrypto(userId: string): void {
    this.walletCryptoService.getUserWalletCrypto(userId).subscribe({
      next: (data) => {
        this.walletCrypto = data;
        console.log('WalletCrypto:', this.walletCrypto);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error loading wallet:', error);
      }
    });
  }




  // Acheter des cryptos
  buyCrypto(userId: string, cryptoName: string, amount: number): void {
    this.walletCryptoService.buyCryptos(userId, cryptoName, amount).subscribe({
      next: (response) => {
        console.log('Buy Crypto Response:', response);
        this.loadUserWalletCrypto(userId); // Rafraîchir les données
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error buying crypto:', error);
      }
    });
  }




// Vendre des cryptos
  vendreSubmit(cryptoName: string, nombre: string): void {
    const amount = parseFloat(nombre);
    if (!cryptoName || amount <= 0) {
      console.error('Invalid input for selling crypto');
      return;
    }
    this.walletCryptoService.setCryptosToSell(this.idUser, cryptoName, amount).subscribe({
      next: (response) => {
        console.log('Sell Crypto Response:', response);
        this.selleCryptos = false;
        this.isClicked = true;
        this.loadUserWalletCrypto(this.idUser);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error selling crypto:', error);
      }
    });
  }




  // Charger toutes les transactions d'un utilisateur
  loadUserTransactions(userId: string): void {
    this.walletCryptoService.getUserTransaction(userId).subscribe({
      next: (data) => {
        this.transactions = data;
        console.log('Transactions:', this.transactions);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error loading transactions:', error);
      }
    });
  }




  // Convertir des cryptos en argent
  transferCryptoToMoney(userId: string, cryptoName: string, nombre: string): void {
    const amount = parseFloat(nombre);
    if (!cryptoName || amount <= 0 || isNaN(amount)) {
      console.error('Invalid input for transferring crypto to money');
      return;
    }
    this.walletCryptoService.transferCryptosToMoney(userId, cryptoName, amount).subscribe({
      next: (response) => {
        console.log('Transfer to Money Response:', response);
        this.transferCryptos = false;
        this.isClicked = true;
        this.loadUserWalletCrypto(userId);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error transferring crypto to money:', error);
      }
    });
  }


  transferSubmit(cryptoName: string, nombre: string): void {
    const amount = parseFloat(nombre);
    this.transferCryptoToMoney(this.idUser, cryptoName, nombre);
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



}
