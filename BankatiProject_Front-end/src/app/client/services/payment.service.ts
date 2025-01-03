import { Injectable } from '@angular/core';

import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import {catchError, Observable, throwError} from 'rxjs';
import {FeedDetails} from "../models/feedDetails";
import {PaymentDetails} from "../models/payment";
import {FeedResponse} from "../models/feedResponse";
import {PaymentResponse} from "../models/paymentResponse";
import {Transaction} from "../models/transaction";
import {SharedInfosService} from "../../service/shared-infos.service";


@Injectable({
  providedIn: 'root'
})


export class PaymentService {

  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }


  public getAllAgents(): Observable<any> {
    let dataUrl: string = `${this.serverUrl}/listAgent`;

    return this.httpClient.get(dataUrl).pipe(catchError(this.handleError));
  }


  public feedPaymentAccount(feedDetails : FeedDetails): Observable<FeedResponse> {

    let dataUrl: string = `${this.serverUrl}/feedAccount`;
    return this.httpClient.put<FeedResponse>(dataUrl, feedDetails).pipe(catchError(this.handleError));

  }


  public PayService(transaction: Transaction): Observable<string> {

    let dataUrl: string = `${this.serverUrl}/create-transaction`;
    return ;
  }


  createSubscriptionTransaction(clientId: string, agentId: string, amount: number): Observable<string> {
    const params = new HttpParams()
      .set('userId', clientId)
      .set('agentId', agentId)
      .set('price', amount.toString())
      .set('durationInMonths', '1'); // Fixé à 1 mois
    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient.post(`${this.serverUrl}/creat-subscription`, null, {
      params,
      headers,
      responseType: 'text'  // Attend une réponse texte brute
    });
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


}
