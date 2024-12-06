import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Payment } from './payment';

describe('MapsComponent', () => {
  let component: Payment;
  let fixture: ComponentFixture<Payment>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Payment ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Payment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
