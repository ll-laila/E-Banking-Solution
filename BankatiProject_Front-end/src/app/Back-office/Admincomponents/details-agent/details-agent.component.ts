import { Component, OnInit } from '@angular/core';
import {IAgent} from "../../../models/Agent";
import {ActivatedRoute} from "@angular/router";
import {AdminService} from "../../../service/admin.service";
import { DatePipe } from '@angular/common';
import {AgentRequest} from '../../../models/AgentRequest';


@Component({
  selector: 'app-details-agent',
  templateUrl: './details-agent.component.html',
  styleUrls: ['./details-agent.component.scss']
})
export class DetailsAgentComponent implements OnInit {

  public agent: AgentRequest = {
    id: '',
    agentId: '',
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    cin: '',
    birthDate: new Date(),
    phoneNumber: '',
    role: '',
    password: '',
    isFirstLogin: false,
    commercialRn: '',
    image: '',
    patentNumber: '',
    isPaymentAccountActivated: false,
    typeHissab: '',
    currency: '',
    token: ''
  };

  constructor(private route: ActivatedRoute, private adminService: AdminService,private datePipe: DatePipe) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.adminService.getUserById(id).subscribe((agent) => {
      this.agent = agent;
    });
  }

  formatDate(date: string | Date) {
    return this.datePipe.transform(date, 'dd-MM-yyyy');
  }
}
