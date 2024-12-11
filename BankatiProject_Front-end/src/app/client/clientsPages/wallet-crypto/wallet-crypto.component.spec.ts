import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WalletCryptoComponent } from './wallet-crypto.component';

describe('WalletCryptoComponent', () => {
  let component: WalletCryptoComponent;
  let fixture: ComponentFixture<WalletCryptoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WalletCryptoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WalletCryptoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
