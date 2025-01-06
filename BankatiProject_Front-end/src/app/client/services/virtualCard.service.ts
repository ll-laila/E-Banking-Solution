import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {VirtualCard} from "../../models/VirtualCard";


@Injectable({
  providedIn: 'root'
})
export class VirtualCardService {

  private apiUrl = 'http://localhost:8222/api/v1/users/virtualcard';

  constructor(private http: HttpClient) { }

  // Créer une carte virtuelle
  createCard(userId: string): Observable<VirtualCard> {
    return this.http.post<VirtualCard>(`${this.apiUrl}/create/${userId}`, {});
  }

  // Activer une carte
  activateCard(cardId: string): Observable<VirtualCard> {
    return this.http.patch<VirtualCard>(`${this.apiUrl}/activate/${cardId}`, {});
  }

  // Désactiver une carte
  deactivateCard(cardId: string): Observable<VirtualCard> {
    return this.http.patch<VirtualCard>(`${this.apiUrl}/deactivate/${cardId}`, {});
  }

  // Récupérer toutes les cartes d'un utilisateur
  getCardsByUser(userId: string): Observable<VirtualCard[]> {
    return this.http.get<VirtualCard[]>(`${this.apiUrl}/user/${userId}`);
  }
}
