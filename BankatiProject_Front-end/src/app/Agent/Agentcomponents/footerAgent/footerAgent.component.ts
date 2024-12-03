import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer-agent',
  templateUrl: './footerAgent.component.html',
  styleUrls: ['./footerAgent.component.scss']
})
export class FooterAgentComponent implements OnInit {
  test: Date = new Date();

  constructor() { }

  ngOnInit() {
  }

}
