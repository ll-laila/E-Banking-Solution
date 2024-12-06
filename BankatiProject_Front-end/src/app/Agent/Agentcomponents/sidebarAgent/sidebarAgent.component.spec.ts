import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarAgentComponent } from './sidebarAgent.component';

describe('SidebarComponent', () => {
  let component: SidebarAgentComponent;
  let fixture: ComponentFixture<SidebarAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SidebarAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidebarAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
