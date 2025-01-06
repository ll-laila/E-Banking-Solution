import { Component, OnInit } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import {IAgent} from "../../../models/Agent";
import {SharedAgentService} from "../../../service/shared-agent.service";
import {SharedInfosService} from "../../../service/shared-infos.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './profileAgent.component.html',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  styleUrls: ['./profileAgent.component.scss']
})
export class ProfileAgentComponent implements OnInit {

  phoneNumber: string;
  firstName: string;
  lastName: string;
  email: string;
  cin:string;
  address: string;

  constructor(private sharedInfosService: SharedInfosService) { }


  ngOnInit(): void {
   this.phoneNumber = this.sharedInfosService.getPhoneNumber();
   this.firstName = this.sharedInfosService.getFirstName();
   this.lastName = this.sharedInfosService.getLastName();
   this.email = this.sharedInfosService.getEmail();
   this.address=this.sharedInfosService.getAddress();
    this.cin=this.sharedInfosService.getCin();
  }




}
