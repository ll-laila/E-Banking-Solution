import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FooterAgentComponent } from './footerAgent.component';

describe('FooterComponent', () => {
  let component: FooterAgentComponent;
  let fixture: ComponentFixture<FooterAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FooterAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FooterAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
