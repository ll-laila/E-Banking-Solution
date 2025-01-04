import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {CookieService} from "ngx-cookie-service";
import {SharedInfosService} from "./shared-infos.service";
import { catchError } from 'rxjs/operators';
import { IAgent } from '../models/Agent';
import { AgentRequest } from '../models/AgentRequest';
import { IAdmin } from '../models/Admin';
import {IClient} from "../models/Client";

import { HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private serverUrl = 'http://localhost:8222/api/v1/users';

    constructor(private httpClient: HttpClient, private cookieService: CookieService,private sharedInfosService: SharedInfosService) { }



  // Ajouter un administrateur
  public addAdmin(adminRequest: IAdmin): Observable<IAdmin> {
    const url = `${this.serverUrl}/addAdmin`;
    return this.httpClient.post<IAdmin>(url, adminRequest).pipe(catchError(this.handleError));
  }

  // Ajouter un agent

  public addAgent(agentRequest: AgentRequest): Observable<AgentRequest> {
    const url = `${this.serverUrl}/create-agent`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    console.log('Agent Request:', agentRequest);

    return this.httpClient.post<AgentRequest>(url, agentRequest, { headers });
  }


  // Récupérer un agent par son ID
  public getAgentById(id: string): Observable<AgentRequest> {
    const url = `${this.serverUrl}/getAgent/${id}`;
    return this.httpClient.get<AgentRequest>(url).pipe(catchError(this.handleError));
  }

  // Mettre à jour un agent
  public updateAgent(id: string, agentRequest: AgentRequest): Observable<AgentRequest> {
    const url = `${this.serverUrl}/updateAgent/${id}`;
    return this.httpClient.put<AgentRequest>(url, agentRequest).pipe(catchError(this.handleError));
  }

  // Supprimer un utilisateur (administrateur ou agent)
  public deleteUser(id: string): Observable<void> {
    const url = `${this.serverUrl}/delete/${id}`;
    return this.httpClient.delete<void>(url).pipe(catchError(this.handleError));
  }

  // Lister tous les agents
  public getAllAgents(): Observable<AgentRequest[]> {
    const url = `${this.serverUrl}/agents`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<AgentRequest[]>(url,{headers});
  }

  // Gestion des erreurs
  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Erreur côté client
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Erreur côté serveur
      errorMessage = `Status: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(errorMessage);
  }
  public getAllClients(): Observable<IClient[]> {

    const dataUrl = `${this.serverUrl}/client/allClients`;

    return this.httpClient.get<IClient[]>(dataUrl, ).pipe(catchError(this.handleError));
  }
}
