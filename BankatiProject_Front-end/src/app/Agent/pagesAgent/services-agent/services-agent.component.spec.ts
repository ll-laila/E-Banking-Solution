import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicesAgentComponent } from './services-agent.component';

describe('ServicesAgentComponent', () => {
  let component: ServicesAgentComponent;
  let fixture: ComponentFixture<ServicesAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServicesAgentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicesAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
