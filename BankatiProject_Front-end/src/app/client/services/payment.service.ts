import { Injectable } from '@angular/core';

import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { map } from 'rxjs/operators';
import {catchError, Observable, throwError} from 'rxjs';
import {FeedDetails} from "../models/feedDetails";
import {PaymentDetails} from "../models/payment";
import {FeedResponse} from "../models/feedResponse";
import {PaymentResponse} from "../models/paymentResponse";


@Injectable({
  providedIn: 'root'
})


export class PaymentService {

  private serverUrl: string = `http://localhost:9090/cmi/service`;
  private authorization = this.cookieService.get('Authorization');


  constructor(private httpClient: HttpClient, private cookieService: CookieService) { }


  public getAllAgents(): Observable<any> {
    let dataUrl: string = `${this.serverUrl}/allCreditors`;
    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get(dataUrl, { headers }).pipe(catchError(this.handleError));
  }


  public feedPaymentAccount(feedDetails : FeedDetails): Observable<FeedResponse> {

    const headers = {
      'Authorization': `${this.authorization}`
    };
    let dataUrl: string = `${this.serverUrl}/feedAccount`;
    return this.httpClient.put<FeedResponse>(dataUrl, feedDetails, {headers}).pipe(catchError(this.handleError));

  }


  public PayService(paymentDetails : PaymentDetails): Observable<PaymentResponse> {

    const headers = {
      'Authorization': `${this.authorization}`
    };
    let dataUrl: string = `${this.serverUrl}/payService`;
    return this.httpClient.put<PaymentResponse>(dataUrl, paymentDetails, {headers}).pipe(catchError(this.handleError));

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
