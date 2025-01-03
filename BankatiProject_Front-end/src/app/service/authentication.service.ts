import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import {SharedInfosService} from "./shared-infos.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl = 'http://localhost:8222/api/v1/users/login'; // Update with your backend URL

  constructor(private http: HttpClient, private sharedInfosService: SharedInfosService) { }

  // Method to handle login
  login(credentials: { phoneNumber: string, password: string }): Observable<any> {
    return this.http.post(this.apiUrl, credentials).pipe(
      tap((response: any) => {
        this.sharedInfosService.setLoginData(response);
      })
    );
  }

 /* // Method to store the token
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Method to retrieve the token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Method to check if the user is logged in
  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Method to log out
  logout(): void {
    localStorage.removeItem('token'); // Remove the token
  }*/
}
