import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferToClientComponent } from './transfer-to-client.component';

describe('TransferToClientComponent', () => {
  let component: TransferToClientComponent;
  let fixture: ComponentFixture<TransferToClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferToClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransferToClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
