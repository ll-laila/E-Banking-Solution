import { Injectable } from '@angular/core';

import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import {catchError, Observable, throwError} from 'rxjs';
import {FeedDetails} from "../models/feedDetails";
import {PaymentDetails} from "../models/payment";
import {FeedResponse} from "../models/feedResponse";
import {PaymentResponse} from "../models/paymentResponse";
import {Transaction} from "../models/transaction";


@Injectable({
  providedIn: 'root'
})


export class PaymentService {

  private serverUrl: string =  `http://localhost:8222/api/v1/users`;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }


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
