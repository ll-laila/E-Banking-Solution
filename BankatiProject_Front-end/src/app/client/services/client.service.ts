import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { Operation } from '../models/operation';
import { ClientRequest } from '../models/clientRequest';
import { SharedInfosService } from '../../service/shared-infos.service';
import { TransactionResponse } from '../models/transactionResponse';
import { Transaction } from '../models/transaction';
import { CookieService } from 'ngx-cookie-service';
import {Wallet} from "../../models/wallet";
import {DepenseResponse} from "../../models/DepenseResponse";
import {DepenseRequest} from "../../models/DepenseRequest";



@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private serverUrl: string = `http://localhost:8222/api/v1/users`;
  private authorization = this.cookieService.get('Authorization');

  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }

//-----------------------------laila---------------------------------------------------//
  public getUserById(id: string): Observable<ClientRequest> {
    const url = `${this.serverUrl}/getUser/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<ClientRequest>(url,{ headers });
  }

  getWalletByClientId(clientId: string): Observable<Wallet> {
    const url = `${this.serverUrl}/wallet/${clientId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<Wallet>(url,{ headers });
  }


  getAllTransactionsByUserId(userId: string): Observable<TransactionResponse[]> {
    const url = `${this.serverUrl}/all-transactions/${userId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<TransactionResponse[]>(url,{ headers });
  }

  feedWallet(clientId: string, amount: number): Observable<boolean> {
    const url = `${this.serverUrl}/feed-wallet`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    const body = { clientId, amount };
    return this.httpClient.post<boolean>(url,body,{ headers });
  }


//-------------------------------------------------------------------------------------//






//-----------------------------------salwa-----------------------------------------------//

//her
//-------------------------------------------------------------------------------------//







//-----------------------------------others--------------------------------------------------//

  public getAgentServiceById(agentId : string): Observable<any> {
    let dataUrl: string = `${this.serverUrl}/serviceByAgent/675d6abc98a04453154ddc30`;
    return this.httpClient.get(dataUrl).pipe(catchError(this.handleError));
  }



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


//-------------------------------------------------------------------------------------//
//------------------- Kaoutar -------------------//
  public createDepense(userId: string,userPhone: string, nouveauMontant: number): Observable<DepenseResponse> {
    const url = `${this.serverUrl}/create-depense`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    const body = { userId, userPhone, nouveauMontant };
    return this.httpClient.post<DepenseResponse>(url, body, { headers });
  }

  public updateDepense(depenseId: string, nouveauMontant: number): Observable<DepenseResponse> {
    const url = `${this.serverUrl}/update-depense/${depenseId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.put<DepenseResponse>(url, { nouveauMontant }, { headers }).pipe(catchError(this.handleError));
  }

  public cancelDepense(depenseId: string): Observable<DepenseResponse> {
    const url = `${this.serverUrl}/cancel-depense/${depenseId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.delete<DepenseResponse>(url, { headers }).pipe(catchError(this.handleError));
  }

  public getDepenseById(depenseId: string): Observable<DepenseRequest> {
    const url = `${this.serverUrl}/get-depense/${depenseId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.get<DepenseRequest>(url, { headers });
  }

  public getAllDepensesByUser(userId: string): Observable<DepenseResponse[]> {
    const url = `${this.serverUrl}/list-depense?userId=${userId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<DepenseResponse[]>(url, { headers }).pipe(catchError(this.handleError));
  }




}
