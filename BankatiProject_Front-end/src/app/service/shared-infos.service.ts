import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedInfosService {


  private phoneNumber: string;
  constructor() { }

  setPhoneNumber(phoneNumber: string) {
    this.phoneNumber = phoneNumber;
  }

  getPhoneNumber(): string {
    return this.phoneNumber;
  }
}
