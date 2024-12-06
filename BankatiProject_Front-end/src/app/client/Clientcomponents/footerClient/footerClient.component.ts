import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer-client',
  templateUrl: './footerClient.component.html',
  styleUrls: ['./footerClient.component.scss']
})
export class FooterClientComponent implements OnInit {
  test: Date = new Date();

  constructor() { }

  ngOnInit() {
  }

}
