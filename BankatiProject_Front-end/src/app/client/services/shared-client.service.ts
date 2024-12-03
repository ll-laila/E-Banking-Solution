import { Injectable } from '@angular/core';
import { Client } from '../models/client';

@Injectable({
  providedIn: 'root'
})
export class SharedClientService {
  private client: Client;
  constructor() { }

  setClient(client: Client) {
    this.client = client;
  }

  getClient(): Client {
    return this.client;
  }
}
