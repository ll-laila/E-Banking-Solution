import { TestBed } from '@angular/core/testing';

import { SharedAgentService } from './shared-agent.service';

describe('SharedAgentService', () => {
  let service: SharedAgentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedAgentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
