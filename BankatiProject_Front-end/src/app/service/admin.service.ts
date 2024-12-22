import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { IAgent } from '../models/Agent';
import { IAdmin } from '../models/Admin';
import {IClient} from "../models/Client";

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private serverUrl = 'http://localhost:8010/api/v1/users';

  constructor(private httpClient: HttpClient) {}

  // Ajouter un administrateur
  public addAdmin(adminRequest: IAdmin): Observable<IAdmin> {
    const url = `${this.serverUrl}/addAdmin`;
    return this.httpClient.post<IAdmin>(url, adminRequest).pipe(catchError(this.handleError));
  }

  // Ajouter un agent
  public addAgent(agentRequest: IAgent): Observable<IAgent> {
    const url = `${this.serverUrl}/addAgent`;
    return this.httpClient.post<IAgent>(url, agentRequest).pipe(catchError(this.handleError));
  }

  // Récupérer un agent par son ID
  public getAgentById(id: string): Observable<IAgent> {
    const url = `${this.serverUrl}/getAgent/${id}`;
    return this.httpClient.get<IAgent>(url).pipe(catchError(this.handleError));
  }

  // Mettre à jour un agent
  public updateAgent(id: string, agentRequest: IAgent): Observable<IAgent> {
    const url = `${this.serverUrl}/updateAgent/${id}`;
    return this.httpClient.put<IAgent>(url, agentRequest).pipe(catchError(this.handleError));
  }

  // Supprimer un utilisateur (administrateur ou agent)
  public deleteUser(id: string): Observable<void> {
    const url = `${this.serverUrl}/delete/${id}`;
    return this.httpClient.delete<void>(url).pipe(catchError(this.handleError));
  }

  // Lister tous les agents
  public getAllAgents(): Observable<IAgent[]> {
    const url = `${this.serverUrl}/listAgent`;
    return this.httpClient.get<IAgent[]>(url).pipe(catchError(this.handleError));
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
