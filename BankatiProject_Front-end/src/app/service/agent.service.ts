
// import { CookieService } from './cookie.service';

import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {catchError, Observable, throwError} from "rxjs";
import {IAgent} from "../models/Agent";
import {Operation} from "../client/models/operation";
import {IClient} from "../models/Client";
import {IAgentServices} from "../models/AgentServices";
import {UserRequest} from "../models/UserRequest";
import {SharedInfosService} from "./shared-infos.service";
import {UserResponse} from "../models/UserResponse";
import {AgentRequest} from "../models/AgentRequest";
import {IAgentServiceResponse} from "../models/IAgentServiceResponse";



@Injectable({
  providedIn: 'root'
})
export class AgentService {

  private serverUrl = `http://localhost:8222/api/v1/users`;
  private authorization = this.cookieService.get('Authorization');




  constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) {
  }


  /*public createClient(clientRegisterRequest: UserRequest): Observable<string> {
    const dataUrl = `${this.serverUrl}/create-client`;

  public createClient(clientRegisterRequest: IClient): Observable<string> {
    const dataUrl = `${this.serverUrl}/client`;

    const headers = this.sharedInfosService.getAuthHeaders();
    return this.httpClient
      .post<string>(dataUrl, clientRegisterRequest, { headers }) // Les en-têtes doivent être placés dans l'objet des options
      .pipe(catchError(this.handleError));
  }

   */
  public createClient(agentRequest: AgentRequest): Observable<AgentRequest> {
    const url = `${this.serverUrl}/create-client`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    console.log('Client Request:', agentRequest);

    return this.httpClient.post<AgentRequest>(url, agentRequest, { headers });
  }

  public getAllClients(): Observable<IClient[]> {

    const dataUrl = `${this.serverUrl}/client/allClients`;

    return this.httpClient.get<IClient[]>(dataUrl, ).pipe(catchError(this.handleError));
  }



  public getClientsByAgentId(agentId: string): Observable<UserResponse[]> {
    const url = `${this.serverUrl}/clientsByAgent/${agentId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<UserResponse[]>(url,{headers});
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


    const dataUrl = `${this.serverUrl}/client/${id}`;
    console.log("URL de suppression:", dataUrl);
    return this.httpClient.delete<{}>(dataUrl).pipe(catchError(this.handleError));

  }
  public viewDetailsClient(id: number): Observable<{}> {


    const dataUrl = `${this.serverUrl}/client/${id}`;
    console.log("URL de suppression:", dataUrl);
    return this.httpClient.delete<{}>(dataUrl).pipe(catchError(this.handleError));

  }



  public getClient(id: string): Observable<UserResponse> {
    const dataUrl = `${this.serverUrl}/client/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<UserResponse>(dataUrl, {headers}).pipe(catchError(this.handleError));
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
    return this.httpClient.get<any>(`${this.serverUrl}/client/${id}`);
  }

  getAgentOperation(id: number): Observable<Operation[]> {
    const dataUrl = `${this.serverUrl}/operations/${id}`;

    return this.httpClient.get<Operation[]>(dataUrl).pipe(catchError(this.handleError));
  }

  updateClient(id: string, client:  IClient): Observable<IClient>  {
    return this.httpClient.put<any>(`${this.serverUrl}/client/${id}`, client);


  }

  public createService(service: IAgentServices): Observable<IAgentServiceResponse> {
    const url = `${this.serverUrl}/creatService`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.post<IAgentServiceResponse>(url, service,{headers}).pipe(catchError(this.handleError));
  }


  public getAllAgentServices(agentId:string): Observable<IAgentServices[]> {
    const url = `${this.serverUrl}/serviceByAgent/${agentId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<IAgentServices[]>(url,{headers});
  }
  public getAgentServices(agentId:string):Observable<IAgentServices[]>{
    const url = `${this.serverUrl}/service/${agentId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<IAgentServices[]>(url,{headers});
  }

  public deleteService(id: string): Observable<IAgentServiceResponse> {
    const dataUrl = `${this.serverUrl}/deleteService/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.delete<IAgentServiceResponse>(dataUrl,{headers}).pipe(catchError(this.handleError));
  }

  public updateService(service: IAgentServices, serviceId: string): Observable<IAgentServiceResponse> {

    const dataUrl = `${this.serverUrl}/service/${serviceId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.put<IAgentServiceResponse>(dataUrl, service,{headers}).pipe(catchError(this.handleError));
  }


  public getService(serviceId: string): Observable<IAgentServices> {
    const dataUrl = `${this.serverUrl}/serviceById/${serviceId}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<IAgentServices>(dataUrl, {headers}).pipe(catchError(this.handleError));
  }

  getAgentSolde(idAgent: string) {
    const headers = {
      'Authorization': `${this.authorization}`
    };
    const dataUrl = `${this.serverUrl}/AgentSolde/${idAgent}`;
    return this.httpClient.get<number>(dataUrl, {headers}).pipe(catchError(this.handleError));
  }
}
