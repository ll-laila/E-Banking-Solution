import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrasactionAgentComponent } from './trasaction-agent.component';

describe('TrasactionAgentComponent', () => {
  let component: TrasactionAgentComponent;
  let fixture: ComponentFixture<TrasactionAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrasactionAgentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrasactionAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
