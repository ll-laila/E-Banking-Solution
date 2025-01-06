import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MyToken } from 'src/app/models/MyToken';

import { DatePipe } from '@angular/common';

import { AuthenticationService } from '../../service/authentication.service';
import jwtDecode from "jwt-decode";
import { SharedInfosService } from "../../service/shared-infos.service";

@Component({
  selector: 'login',
  templateUrl: 'login.component.html',
  standalone: true,
  imports: [
    FormsModule,
    DatePipe
  ],
  styleUrls: ['login.component.scss']
})
export class LoginComponent implements OnInit {

  public phoneNumber: string;
  public password: string; // Add password field
  test: Date = new Date();

  constructor(
    private router: Router,
    private authService: AuthenticationService, // Use AuthenticationService
    private sharedInfosService: SharedInfosService
  ) {
  }

  ngOnInit(): void {
  }

  // Add this method to handle login
  login(loginForm: NgForm) {
    if (loginForm.valid) {
      const credentials = {
        phoneNumber: this.phoneNumber,
        password: this.password
      };


      console.log('Sending credentials:', credentials); // Log the payload

      this.authService.login(credentials).subscribe({
        next: (response) => {
          // Handle successful login
          console.log('Login response:', response); // Log the response

          const token = response.token; // Assuming the backend returns a token
          this.sharedInfosService.setLoginData(response); // Store the full login data in shared service

          console.log("sharedinfo : ",this.sharedInfosService);

          // Redirect based on role
          //const role = response.role; // Get the role from the response
          const role = this.sharedInfosService.getRole();
          const firstLogin = this.sharedInfosService.getFirstLogin();

          if (role === 'CLIENT') {
            if (firstLogin === true) {
              this.router.navigate(['/client-change-password']);
            } else {
              this.router.navigate(['/client']);
            }
          } else if (role === 'AGENT') {
            this.router.navigate(['/agent']);
          } else if (role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            console.error('Unknown role:', role);
            alert('Invalid role');
          }
        },
        error: (error) => {
          console.error('Login failed:', error);
          console.error('Error status:', error.status);
          console.error('Error message:', error.message);
          console.error('Error response:', error.error);
          alert('Login failed. Please check your credentials.');
        }
      });
    } else {
      alert('Please fill in all required fields.');
    }
  }
}
