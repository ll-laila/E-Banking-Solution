import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarAgentComponent } from './navbarAgent.component';

describe('NavbarComponent', () => {
  let component: NavbarAgentComponent;
  let fixture: ComponentFixture<NavbarAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
