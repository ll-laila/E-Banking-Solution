import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentchangePasswordComponent } from './Agentchange-password.component';

describe('ChangePasswordComponent', () => {
  let component: AgentchangePasswordComponent;
  let fixture: ComponentFixture<AgentchangePasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgentchangePasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentchangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
