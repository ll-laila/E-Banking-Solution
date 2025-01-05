import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SharedInfosService } from './shared-infos.service';

@Injectable({
  providedIn: 'root'
})
export class ClientChangePasswordService {
  private apiUrl = 'http://localhost:8222/api/v1/users/change-password';

  constructor(
    private httpClient: HttpClient,
    private sharedInfosService: SharedInfosService
  ) {}

  // Change password method with role-based authorization
  public changePassword(changePasswordRequest: { phoneNumber: string; oldPassword: string; newPassword: string }): Observable<any> {
    const headers = this.sharedInfosService.getAuthHeaders();
    const role = this.sharedInfosService.getRole();

    console.log('Headers:', headers.get('Authorization'));
    console.log('Change Password Request:', changePasswordRequest);

    if (role !== 'CLIENT') {
      console.error('Unauthorized access: Only CLIENT role can change password.');
      throw new Error('Unauthorized access');
    }

    return this.httpClient.put(`${this.apiUrl}`, changePasswordRequest, { headers });
  }

}
