import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Transaction} from "../models/transaction";
import {Observable} from "rxjs";
import {Client} from "../models/client";
import {TransactionType} from "../models/transaction-type";
import {SharedInfosService} from "../../service/shared-infos.service";
import {UserResponse} from "../../models/UserResponse";
import {UserDto} from "../../models/UserDto";

@Injectable({
  providedIn: 'root'
})
export class TransactionServiceService {
  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }

  createTransaction(senderId: string, beneficiaryId: string, amount: number, transactionType: TransactionType): Observable<string> {
    const params = new HttpParams()
      .set('senderId', senderId)
      .set('beneficiaryId', beneficiaryId)
      .set('amount', amount.toString())
      .set('transactionType', transactionType.toString());
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.post(`${this.serverUrl}/creat-transaction`, null, {
      params,
      headers,
      responseType: 'text'  // Accepte une réponse texte brute
    });
  }

  getClientIdByPhoneNumber(phoneNumber: string): Observable<UserDto> {
    const headers = this.sharedInfosService.getAuthHeaders(); // Récupérer les en-têtes d'authentification
    return this.httpClient.get<UserDto>(`${this.serverUrl}/clientByPhone/${phoneNumber}`, { headers });
  }

  getClientInfo(clientId: string): Observable<UserResponse> {
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.get<UserResponse>(`${this.serverUrl}/clientbyid/${clientId}`,{
      headers // Ajout des en-têtes
    });
  }

}
