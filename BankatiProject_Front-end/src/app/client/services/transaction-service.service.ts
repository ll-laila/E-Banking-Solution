import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Transaction} from "../models/transaction";
import {Observable} from "rxjs";
import {Client} from "../models/client";

@Injectable({
  providedIn: 'root'
})
export class TransactionServiceService {
  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }

   createTransaction(transaction: Transaction): Observable<Transaction> {
    return this.httpClient.post<Transaction>(`${this.serverUrl}/creat-transaction`, transaction);
  }

  getClientIdByPhoneNumber(phoneNumber: string): Observable<string> {
    return this.httpClient.get<string>(`${this.serverUrl}/clientByPhone/${phoneNumber}`);
  }

  getClientInfo(clientId: string): Observable<Client> {
    return this.httpClient.get<Client>(`${this.serverUrl}/clientbyid/${clientId}`);
  }

}
