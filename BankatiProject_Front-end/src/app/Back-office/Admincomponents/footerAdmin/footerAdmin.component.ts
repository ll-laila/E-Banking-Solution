import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footerAdmin.component.html',
  styleUrls: ['./footerAdmin.component.scss']
})
export class FooterAdminComponent implements OnInit {
  test: Date = new Date();

  constructor() { }

  ngOnInit() {
  }

}
