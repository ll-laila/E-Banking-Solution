import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WalletCrypto } from '../models/crypto/wallet-crypto';
import { TransactionCrypto } from '../models/crypto/transaction-crypto';
import { CookieService } from 'ngx-cookie-service';
import { SharedInfosService } from '../../service/shared-infos.service';

 
@Injectable({
  providedIn: 'root'
})
export class WalletCryptoService {
  private serverUrl = `http://localhost:8222/api/v1/users`;


  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) {}

  getPriceCrypto(cryptoName: string): Observable<number> {
    const url = `${this.serverUrl}/getActualPrice/${cryptoName}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<number>(url,{ headers });
  }


  getBalanceWallet(clientId: string): Observable<number> {
    const url = `${this.serverUrl}/userWalletBalance/${clientId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<number>(url,{ headers });
  }

  // Get User Wallet Crypto
  getUserWalletCrypto(userId: string): Observable<WalletCrypto> {
    const url = `${this.serverUrl}/walletCrypto/${userId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<WalletCrypto>(url,{ headers });
  }

  // Buy Cryptos
  buyCryptos(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/buyCryptos`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
    return this.httpClient.post<string>(url,null,{params, headers });
  }

  // Set Cryptos to Sell
  setCryptosToSell(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/setCryptosToSell`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
    return this.httpClient.post<string>(url,null,{params, headers });
  }


  // Get All Transactions for a User
  getUserTransaction(userId: string): Observable<TransactionCrypto[]> {
    const url = `${this.serverUrl}/allTransCrypro/${userId}`;   
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<TransactionCrypto[]>(url,{ headers });
  }

  // Transfer Cryptos to Money
  transferCryptosToMoney(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/transferCryptosToMoney`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
      return this.httpClient.post<string>(url,null,{params, headers });
  }



  // Error Handling
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Status: ${error.status} \nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }
}
