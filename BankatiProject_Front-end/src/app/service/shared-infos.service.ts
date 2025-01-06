import { Injectable } from '@angular/core';
import { HttpHeaders } from "@angular/common/http";

// Define a login response interface
interface LoginResponse {
  id: string;
  agentId: string | null;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  role: string;
  token: string;
  address: string | null;
  birthDate: string | null;
  cin: string | null;
  createdDate: string | null;
  commercialRn: string | null;
  currency: string | null;
  firstLogin: string;
  isPaymentAccountActivated: string | null;
  patentNumber: string | null;
  typeHissab: string | null;
  // image?: string;
  // password?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SharedInfosService {
  private id: string;
  private agentId: string | null;
  private firstName: string;
  private lastName: string;
  private email: string;
  private phoneNumber: string;
  private role: string;
  private token: string | null = null;
  private address: string | null;
  private birthDate: string | null;
  private cin: string | null;
  private createdDate: string | null;
  private commercialRn: string | null;
  private currency: string | null;
  private firstLogin: boolean;
  private isPaymentAccountActivated: string | null;
  private patentNumber: string | null;
  private typeHissab: string | null;

  constructor() { }

  // Setters
  setLoginData(response: LoginResponse): void {
    this.id = response.id;
    this.agentId = response.agentId;
    this.firstName = response.firstName;
    this.lastName = response.lastName;
    this.email = response.email;
    this.phoneNumber = response.phoneNumber;
    this.role = response.role;
    this.token = response.token;
    this.address = response.address;
    this.birthDate = response.birthDate;
    this.cin = response.cin;
    this.createdDate = response.createdDate;
    this.commercialRn = response.commercialRn;
    this.currency = response.currency;
    this.firstLogin = Boolean(response.firstLogin);
    this.isPaymentAccountActivated = response.isPaymentAccountActivated;
    this.patentNumber = response.patentNumber;
    this.typeHissab = response.typeHissab;

    localStorage.setItem('token', this.token);
    localStorage.setItem('id',response.id);
    localStorage.setItem('phoneNumber',response.phoneNumber);
    localStorage.setItem('agentId',response.id);
  }

  // Getters
  getId(): string {
    return this.id;
  }

  getAgentId(): string | null {
    return this.agentId;
  }

  getFirstName(): string {
    return this.firstName;
  }

  getLastName(): string {
    return this.lastName;
  }

  getEmail(): string {
    return this.email;
  }

  getPhoneNumber(): string {
    return this.phoneNumber;
  }

  getRole(): string {
    return this.role;
  }

  getToken(): string | null {
    return this.token || localStorage.getItem('token');
  }
  getAddress(): string | null {
    return this.address || null;
  }
  getCin(): string | null {
    return this.cin;
  }

  getFirstLogin(): boolean {
    return this.firstLogin;
  }

  getAuthHeaders(): HttpHeaders {
    const token = this.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // Clear all stored information (used for logout)
  clear(): void {
    this.id = '';
    this.agentId = null;
    this.firstName = '';
    this.lastName = '';
    this.email = '';
    this.phoneNumber = '';
    this.role = '';
    this.token = null;
    this.address = null;
    this.birthDate = null;
    this.cin = null;
    this.createdDate = null;
    this.commercialRn = null;
    this.currency = null;
    this.firstLogin = true;
    this.isPaymentAccountActivated = null;
    this.patentNumber = null;
    this.typeHissab = null;

    localStorage.removeItem('token');
  }
}
