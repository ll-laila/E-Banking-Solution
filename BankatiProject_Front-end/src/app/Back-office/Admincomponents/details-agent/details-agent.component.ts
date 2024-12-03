import { Component, OnInit } from '@angular/core';
import {IAgent} from "../../../models/Agent";
import {ActivatedRoute} from "@angular/router";
import {AgentService} from "../../../service/agent.service";
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-details-agent',
  templateUrl: './details-agent.component.html',
  styleUrls: ['./details-agent.component.scss']
})
export class DetailsAgentComponent implements OnInit {
  agent: IAgent;

  constructor(private route: ActivatedRoute, private agentService: AgentService,private datePipe: DatePipe) { }

  ngOnInit(): void {

  }

  formatDate(date: string | Date) {
    return this.datePipe.transform(date, 'dd-MM-yyyy');
  }
}
