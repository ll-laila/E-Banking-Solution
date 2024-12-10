
// import { CookieService } from './cookie.service';

import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {catchError, Observable, throwError} from "rxjs";
import {IAgent} from "../models/Agent";
import {Operation} from "../client/models/operation";
import {IClient} from "../models/Client";


@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private serverUrl = `http://localhost:8010/api/v1/users/client`;


  private authorization = this.cookieService.get('Authorization');

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {
  }

  public createClient(clientRegisterRequest: IClient): Observable<IClient> {

    const dataUrl = `${this.serverUrl}`;
    return this.httpClient.post<IClient>(dataUrl, clientRegisterRequest)
      .pipe(catchError(this.handleError));
  }


  public getAllClients(): Observable<IClient[]> {

    const dataUrl = `${this.serverUrl}/allClients`;

    return this.httpClient.get<IClient[]>(dataUrl, ).pipe(catchError(this.handleError));
  }
  //const dataUrl = `${this.serverUrl}/listByAgent/${idAgent}`;
  //console.log(this.authorization);
/*
  public getAllClientsByAgentId(idagent: number): Observable<IClient[]> {

    const dataUrl = `${this.serverUrl}/listByAgent/${idagent}`;
    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get<IClient[]>(dataUrl, {headers}).pipe(catchError(this.handleError));
  }

  */



  public deleteClient(id: number): Observable<{}> {


    const dataUrl = `${this.serverUrl}${id}`;
    console.log("URL de suppression:", dataUrl);
    return this.httpClient.delete<{}>(dataUrl).pipe(catchError(this.handleError));

  }
  public viewDetailsClient(id: number): Observable<{}> {


    const dataUrl = `${this.serverUrl}/${id}`;
    console.log("URL de suppression:", dataUrl);
    return this.httpClient.delete<{}>(dataUrl).pipe(catchError(this.handleError));

  }



  public getClient(id: number): Observable<IClient> {
    const headers = {
      'Authorization': `${this.authorization}`
    };
    const dataUrl = `${this.serverUrl}/${id}`;
    return this.httpClient.get<IClient>(dataUrl, {headers}).pipe(catchError(this.handleError));
  }

  public handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client error
      errorMessage = `Error : ${error.error.message}`;
    } else {
      // server error
      errorMessage = `Status : ${error.status} \n Message: ${error.message}`;
    }
    return throwError(errorMessage);
  }


  getAgentById(id: string): Observable<any> {
    return this.httpClient.get<any>(`${this.serverUrl}/${id}`);
  }

  getAgentOperation(id: number): Observable<Operation[]> {
    const dataUrl = `${this.serverUrl}/operations/${id}`;
    const headers = {
      'Authorization': `${this.authorization}`
    };
    return this.httpClient.get<Operation[]>(dataUrl, {headers}).pipe(catchError(this.handleError));
  }
}
