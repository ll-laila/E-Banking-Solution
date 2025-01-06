import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {ClientChangePasswordService} from "../../service/client-change-password.service";
import {Router} from "@angular/router";  // For notifications

@Component({
  selector: 'app-client-change-password',
  templateUrl: './client-change-password.component.html',
  styleUrls: ['./client-change-password.component.scss']
})
export class ClientChangePasswordComponent {
  phoneNumber: string = '';
  newPassword: string = '';
  oldPassword: string = '';
  test: Date = new Date();


  constructor(
    private changePasswordService: ClientChangePasswordService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  // Method to handle form submission
  onSubmit(): void {
    if (this.newPassword && this.oldPassword && this.phoneNumber) {
      const changePasswordRequest = {
        phoneNumber: this.phoneNumber,
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
      };

      this.changePasswordService.changePassword(changePasswordRequest).subscribe(
        () => {
          this.toastr.success('Mot de passe changé avec succès', 'Succès');
          this.router.navigate(['/client']).then();
        },
        (error) => {
          this.toastr.error('Erreur lors du changement de mot de passe', 'Erreur');
          console.error('Error changing password:', error);
        }
      );
    } else {
      this.toastr.warning('Veuillez remplir tous les champs', 'Attention');
    }
  }



}
