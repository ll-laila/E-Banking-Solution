import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BudgetPersonelComponent } from './budget-personel.component';

describe('BudgetPersonelComponent', () => {
  let component: BudgetPersonelComponent;
  let fixture: ComponentFixture<BudgetPersonelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BudgetPersonelComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BudgetPersonelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
