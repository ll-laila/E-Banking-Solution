import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { Operation } from '../models/operation';
import { Client } from '../models/client';

import { Transaction } from '../models/transaction';
import { CookieService } from 'ngx-cookie-service';
import {Wallet} from "../../models/wallet";



@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private serverUrl: string = `http://localhost:8222/api/v1/users`;
  private authorization = this.cookieService.get('Authorization');

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }


  public getAgentServiceById(agentId : string): Observable<any> {
    let dataUrl: string = `${this.serverUrl}/serviceByAgent/675d6abc98a04453154ddc30`;
    return this.httpClient.get(dataUrl).pipe(catchError(this.handleError));
  }


  //public getClientById(clientId : number): Observable<any> {
    //let dataUrl: string = `${this.serverUrl}/Profile/${clientId}`;
    //console.log(this.authorization);

    //const headers = {
      //'Authorization': `${this.authorization}`
    //};
    //return this.httpClient.get(dataUrl, { headers }).pipe(catchError(this.handleError));
  //}


  public getPaymentAccountByClientId(clientId : number): Observable<any> {
    const dataUrl: string = `${this.serverUrl}/PaymentAccount/${clientId}`;
    console.log(this.authorization);

    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get(dataUrl, { headers }).pipe(catchError(this.handleError));
  }


  getClientByPhoneNumber(phoneNumber: String): Observable<any> {
    let dataUrl: string = `${this.serverUrl}/Profile/Phone/${phoneNumber}`;
    console.log(this.authorization);

    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get(dataUrl, { headers }).pipe(catchError(this.handleError));
  }


  getClientOperation(phoneNumber: string): Observable<Operation[]> {
    let dataUrl: string = `${this.serverUrl}/history/${phoneNumber}`;
    console.log(this.authorization);

    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get<Operation[]>(dataUrl, { headers }).pipe(catchError(this.handleError));
  }

  //Error Handling
  public handleError(error: HttpErrorResponse){
    let errorMessage: string = '';
    if(error.error instanceof ErrorEvent) {
      // client error
      errorMessage = `Error : ${error.error.message}`
    } else {
      // server error
      errorMessage = `Status : ${error.status} \n Message: ${error.message}`
    }
    return throwError(errorMessage);
  }

//kaoutar
   getClientById(clientId: string): Observable<Client> {
    return this.httpClient.get<Client>(`${this.serverUrl}/clientbyid/${clientId}`);
  }

  getWalletByClientId(clientId: string): Observable<Wallet> {
    return this.httpClient.get<Wallet>(`${this.serverUrl}/wallet/${clientId}`);
  }
  getTransactionsByUserId(userId: string): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(`${this.serverUrl}/transactions/${userId}`);
  }

  getAllTransactionsByUserId(userId: string): Observable<Transaction[]> {
    return this.httpClient.get<Transaction[]>(`${this.serverUrl}/all-transactions/${userId}`);
  }

  feedWallet(clientId: string, amount: number): Observable<boolean> {
    const body = { clientId, amount };
    return this.httpClient.post<boolean>(`${this.serverUrl}/feed-wallet`, body);
  }


}
