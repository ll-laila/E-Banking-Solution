import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Transaction} from "../models/transaction";
import {catchError, Observable, throwError} from "rxjs";
import {Client} from "../models/client";
import {TransactionType} from "../models/transaction-type";
import { SharedInfosService } from '../../service/shared-infos.service';
import { ClientRequest } from '../models/clientRequest';
import { map } from 'rxjs/operators';
import {AgentRequest} from "../../models/AgentRequest";
import {UserResponse} from "../../models/UserResponse";
import {Subscription} from "../../models/Subscription";

@Injectable({
  providedIn: 'root'
})
export class TransactionServiceService {
  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }

  createTransaction(senderId: string, beneficiaryId: string, amount: number, transactionType: TransactionType): Observable<string>{
    const url = `${this.serverUrl}/creat-transaction`;
    const params = new HttpParams()
      .set('senderId', senderId)
      .set('beneficiaryId', beneficiaryId)
      .set('amount', amount.toString())
      .set('transactionType', transactionType.toString());

    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.post(url, null, {
      params,
      headers,
      responseType: 'text'
    });
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

  getUserSubscriptions(userId: string): Observable<Subscription[]> {
    const url = `${this.serverUrl}/subscriptions/${userId}`;
    const headers = this.sharedInfosService.getAuthHeaders();

    return this.httpClient.get<Subscription[]>(url, { headers }).pipe(
      catchError((error) => {
        console.error('Erreur lors de la récupération des abonnements :', error);
        return throwError(() => new Error('Erreur lors de la récupération des abonnements'));
      })
    );
  }



  public getClientInfos(id: string): Observable<UserResponse> {
      const url = `${this.serverUrl}/client/${id}`;
      const headers = this.sharedInfosService.getAuthHeaders();
      console.log('Headers:', headers.get('Authorization'));
      return this.httpClient.get<UserResponse>(url,{ headers });
  }




}
