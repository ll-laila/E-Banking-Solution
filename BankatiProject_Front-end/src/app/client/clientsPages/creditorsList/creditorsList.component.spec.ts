import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditorsListComponent } from './creditorsList.component';

describe('IconsComponent', () => {
  let component: CreditorsListComponent;
  let fixture: ComponentFixture<CreditorsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditorsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditorsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
