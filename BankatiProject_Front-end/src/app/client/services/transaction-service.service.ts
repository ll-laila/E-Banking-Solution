import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Transaction} from "../models/transaction";
import {Observable} from "rxjs";
import {Client} from "../models/client";
import {TransactionType} from "../models/transaction-type";

@Injectable({
  providedIn: 'root'
})
export class TransactionServiceService {
  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

  createTransaction(senderId: string, beneficiaryId: string, amount: number, transactionType: TransactionType): Observable<string> {
    const params = new HttpParams()
      .set('senderId', senderId)
      .set('beneficiaryId', beneficiaryId)
      .set('amount', amount.toString())
      .set('transactionType', transactionType.toString());

    return this.httpClient.post(`${this.serverUrl}/creat-transaction`, null, {
      params,
      responseType: 'text'  // Accepte une réponse texte brute
    });
  }

  getClientIdByPhoneNumber(phoneNumber: string): Observable<string> {
    return this.httpClient.get(`${this.serverUrl}/clientByPhone/${phoneNumber}`, { responseType: 'text' }) as Observable<string>;
  }

  getClientInfo(clientId: string): Observable<Client> {
    return this.httpClient.get<Client>(`${this.serverUrl}/clientbyid/${clientId}`);
  }

}
