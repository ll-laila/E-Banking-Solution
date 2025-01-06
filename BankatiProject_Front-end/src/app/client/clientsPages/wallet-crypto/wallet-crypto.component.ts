import { Component, OnInit } from '@angular/core';
import {WalletCryptoService} from '../../services/wallet-crypto.service';
import {TransactionCrypto} from '../../models/crypto/transaction-crypto';
import {WalletCrypto} from '../../models/crypto/wallet-crypto';
import { Router } from '@angular/router';
 
@Component({
  selector: 'app-wallet-crypto',
  templateUrl: './wallet-crypto.component.html',
  styleUrls: ['./wallet-crypto.component.scss']
})
export class WalletCryptoComponent implements OnInit {
  isClicked = true;
  allCryptos = false;
  transferCryptos = false;
  transferCryptosTrue = false;
  selleCryptos = false;
  selleCryptosTrue = false;
  isModalOpen = false;
  trueBalance = false;
  trueCryptos = false;
  walletCrypto: WalletCrypto | null = null;
  transactions: TransactionCrypto[] = [];
  errorMessage = '';

  crypto = '';
  nombre = '';

  bitcoinPrice = 0;
  ethereumPrice = 0;
  cryptoName = '';
  cryptoPrice = 0;
  nombreAcheter = 0;
  balance = 0;

  constructor(private walletCryptoService: WalletCryptoService, private router: Router) { }

  ngOnInit(): void {
    this.loadUserWalletCrypto(localStorage.getItem('id'));
    this.loadUserTransactions(localStorage.getItem('id'));
    this.cryptoPrice1('bitcoin');
    this.cryptoPrice2('ethereum');
    this.getBalanceWallet();
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

  acheterCrypto() {
    if (this.balance > (this.cryptoPrice * this.nombreAcheter)) {
      this.buyCrypto(localStorage.getItem('id'), this.cryptoName, this.nombreAcheter);
      this.closeModal();
    } else {
      this.trueBalance = true;
    }
  }





  testCryptoValue(cryptoName: string, nombre: number): boolean {
    const value = this.walletCrypto.cryptos[cryptoName];
    return value >= nombre;
  }




// Vendre des cryptos
  vendreSubmit(cryptoName: string, nombre: string): void {

      const amount = parseFloat(nombre);
      if (!cryptoName || amount <= 0) {
        console.error('Invalid input for selling crypto');
        return;
      }
    if (this.testCryptoValue(cryptoName, amount)) {
      this.walletCryptoService.setCryptosToSell(localStorage.getItem('id'), cryptoName, amount).subscribe({
        next: (response) => {
          console.log('Sell Crypto Response:', response);
          this.crypto = '';
          this.nombre = '';
          this.loadUserWalletCrypto(localStorage.getItem('id'));
        },
        error: (error) => {
          this.errorMessage = error;
          console.error('Error selling crypto:', error);
        }
      });
      this.selleCryptosTrue = true;
    } else {
      this.trueCryptos = true;
    }
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
        window.location.reload();
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Error transferring crypto to money:', error);
      }
    });
    this.transferCryptosTrue = true;
  }






  transferSubmit(cryptoName: string, nombre: string): void {
    const amount = parseFloat(nombre);
    this.transferCryptoToMoney(localStorage.getItem('id'), cryptoName, nombre);
  }







  cryptoPrice1(cryptoName: string): void {
    this.walletCryptoService.getPriceCrypto(cryptoName).subscribe({
      next: (data) => {
        this.bitcoinPrice = data;
        console.log('Prix de la crypto 1:', data);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Erreur lors du chargement du prix :', error);
      }
    });
  }





  cryptoPrice2(cryptoName: string): void {
    this.walletCryptoService.getPriceCrypto(cryptoName).subscribe({
      next: (data) => {
        this.ethereumPrice = data;
        console.log('Prix de la crypto 2:', data);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Erreur lors du chargement du prix :', error);
      }
    });
  }







  // reel money
  getBalanceWallet(): void {
    this.walletCryptoService.getBalanceWallet(localStorage.getItem('id')).subscribe({
      next: (data) => {
        this.balance = data;
        console.log('Balance :', data);
      },
      error: (error) => {
        this.errorMessage = error;
        console.error('Erreur  :', error);
      }
    });
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

  openModal1() {
    this.cryptoName = 'bitcoin';
    this.cryptoPrice = this.bitcoinPrice;
    this.isModalOpen = true;
  }

  openModal2() {
    this.cryptoName = 'ethereum';
    this.cryptoPrice = this.ethereumPrice;
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  goBack(){
    window.location.reload();
  }

}
