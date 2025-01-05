import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WalletCrypto } from '../models/crypto/wallet-crypto';
import { TransactionCrypto } from '../models/crypto/transaction-crypto';
import { CookieService } from 'ngx-cookie-service';


 
@Injectable({
  providedIn: 'root'
})
export class WalletCryptoService {
  private serverUrl = `http://localhost:8222/api/v1/users`;
  private authorization = this.cookieService.get('Authorization');

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {}

  getPriceCrypto(cryptoName: string): Observable<number> {
    const url = `${this.serverUrl}/getActualPrice/${cryptoName}`;
    // const headers = {
   //   'Authorization': `${this.authorization}`
   // };
    return this.httpClient.get<number>(url).pipe(catchError(this.handleError));
  }


  getBalanceWallet(clientId: string): Observable<number> {
    const url = `${this.serverUrl}/userWalletBalance/${clientId}`;
    // const headers = {
    //   'Authorization': `${this.authorization}`
    // };
    return this.httpClient.get<number>(url).pipe(catchError(this.handleError));
  }

  // Get User Wallet Crypto
  getUserWalletCrypto(userId: string): Observable<WalletCrypto> {
    const url = `${this.serverUrl}/walletCrypto/${userId}`;
   // const headers = {
   //   'Authorization': `${this.authorization}`
   // };
    return this.httpClient.get<WalletCrypto>(url).pipe(catchError(this.handleError));
  }

  // Buy Cryptos
  buyCryptos(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/buyCryptos`;
   // const headers = {
  //    'Authorization': `${this.authorization}`
   // };
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
    return this.httpClient.post<string>(url, null, { params }).pipe(catchError(this.handleError));
  }

  // Set Cryptos to Sell
  setCryptosToSell(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/setCryptosToSell`;
   // const headers = {
   //   'Authorization': `${this.authorization}`
   // };
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
    return this.httpClient.post<string>(url, null, { params }).pipe(catchError(this.handleError));
  }


  // Get All Transactions for a User
  getUserTransaction(userId: string): Observable<TransactionCrypto[]> {
    const url = `${this.serverUrl}/allTransCrypro/${userId}`;
  //  const headers = {
    //  'Authorization': `${this.authorization}`
  //  };
    return this.httpClient.get<TransactionCrypto[]>(url).pipe(catchError(this.handleError));
  }

  // Transfer Cryptos to Money
  transferCryptosToMoney(userId: string, cryptoName: string, amount: number): Observable<string> {
    const url = `${this.serverUrl}/transferCryptosToMoney`;
  //  const headers = {
  //    'Authorization': `${this.authorization}`
  //  };
    const params = new HttpParams()
      .set('userId', userId)
      .set('cryptoName', cryptoName)
      .set('amount', amount.toString());
    return this.httpClient.post<string>(url, null, { params }).pipe(catchError(this.handleError));
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
