import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarteVirtuelleComponent } from './carte-virtuelle.component';

describe('CarteVirtuelleComponent', () => {
  let component: CarteVirtuelleComponent;
  let fixture: ComponentFixture<CarteVirtuelleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarteVirtuelleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarteVirtuelleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
