import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Transaction} from "../models/transaction";
import {Observable} from "rxjs";
import {Client} from "../models/client";
import {TransactionType} from "../models/transaction-type";
import { SharedInfosService } from '../../service/shared-infos.service';
import { ClientRequest } from '../models/clientRequest';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TransactionServiceService {
  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }

  createTransaction(senderId: string, beneficiaryId: string, amount: number, transactionType: TransactionType): Observable<string> {
        const url = `${this.serverUrl}/creat-transaction`;
        const params = new HttpParams()
        .set('senderId', senderId)
        .set('beneficiaryId', beneficiaryId)
        .set('amount', amount.toString())
        .set('transactionType', transactionType.toString());
        const headers = this.sharedInfosService.getAuthHeaders();
        console.log('Headers:', headers.get('Authorization'));
        return this.httpClient.post<string>(url,null,{ params , headers });
  }

  getClientIdByPhoneNumber(phoneNumber: string): Observable<string> {
    const url = `${this.serverUrl}/getUserByPhone/${phoneNumber}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
  
    return this.httpClient.get(url, { headers, responseType: 'text' }).pipe(
      map(response => {
        try {
          return JSON.parse(response);
        } catch (error) {
          console.error('Erreur lors du parsing JSON:', error);
          return response;
        }
      })
    );
  }
  


   public getClientInfos(id: string): Observable<ClientRequest> {
      const url = `${this.serverUrl}/getUser/${id}`;
      const headers = this.sharedInfosService.getAuthHeaders();
      console.log('Headers:', headers.get('Authorization'));
      return this.httpClient.get<ClientRequest>(url,{ headers });
    }


  getClientInfo(clientId: string): Observable<Client> {
    return this.httpClient.get<Client>(`${this.serverUrl}/clientbyid/${clientId}`);
  }

}
