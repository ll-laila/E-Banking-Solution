import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {VirtualCard} from "../../models/VirtualCard";
import {ClientRequest} from "../models/clientRequest";
import {SharedInfosService} from "../../service/shared-infos.service";


@Injectable({
  providedIn: 'root'
})
export class VirtualCardService {

  private apiUrl = 'http://localhost:8222/api/v1/users';

  constructor(private http: HttpClient,private sharedInfosService: SharedInfosService) { }

  // Créer une carte virtuelle
  createCard(userId: string): Observable<VirtualCard> {
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.http.post<VirtualCard>(`${this.apiUrl}/virtualcard/create/${userId}`,null, {headers});
  }
  feedCard(clientId: string,solde :string): Observable<VirtualCard> {
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
   // const body = { clientId, solde };
    return this.http.post<VirtualCard>(`${this.apiUrl}/feed-card/${clientId}/${solde}`,null, {headers});
  }

  // Activer une carte
  activateCard(cardId: string): Observable<VirtualCard> {
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.http.patch<VirtualCard>(`${this.apiUrl}/virtualcard/activate/${cardId}`,null, {headers});
  }

  // Désactiver une carte
  deactivateCard(cardId: string): Observable<VirtualCard> {
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.http.patch<VirtualCard>(`${this.apiUrl}/virtualcard/deactivate/${cardId}` ,null,{headers});
  }

  // Récupérer toutes les cartes d'un utilisateur
  getCardsByUser(userId: string): Observable<VirtualCard> {
    const headers = this.sharedInfosService.getAuthHeaders();
    console.log('Headers:', headers.get('Authorization'));
    return this.http.get<VirtualCard>(`${this.apiUrl}/virtualcard/user/${userId}`,{headers});
  }
}
