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



  // Ajouter un agent

  public addAgent(agentRequest: AgentRequest): Observable<AgentRequest> {
    const url = `${this.serverUrl}/create-agent`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    console.log('Agent Request:', agentRequest);
    return this.httpClient.post<AgentRequest>(url, agentRequest, { headers });
  }


  // Récupérer un agent par son ID
  public getUserById(id: string): Observable<AgentRequest> {
    const url = `${this.serverUrl}/getUser/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<AgentRequest>(url,{ headers });
  }

  // Mettre à jour un agent
  public updateAgent(id: string, agentRequest: AgentRequest): Observable<AgentRequest> {
    const url = `${this.serverUrl}/updateAgent/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.put<AgentRequest>(url, agentRequest,{ headers });
  }

  // Supprimer un utilisateur (administrateur ou agent)
  public deleteUser(id: string): Observable<void> {
    const url = `${this.serverUrl}/delete/${id}`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.delete<void>(url,{headers});
  }

  // Lister tous les agents
  public getAllAgents(): Observable<AgentRequest[]> {
    const url = `${this.serverUrl}/agents`;
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.httpClient.get<AgentRequest[]>(url,{headers});
  }


  // Lister tous les clients
  public getAllClients(): Observable<AgentRequest[]> {
    const url = `${this.serverUrl}/clients`;
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

}
