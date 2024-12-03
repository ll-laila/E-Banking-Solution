import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentDashComponent } from './agent-dash.component';

describe('AdminLayoutComponent', () => {
  let component: AgentDashComponent;
  let fixture: ComponentFixture<AgentDashComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgentDashComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgentDashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
