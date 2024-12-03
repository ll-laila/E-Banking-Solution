import { TestBed } from '@angular/core/testing';

import { SharedAgentServiceService } from './shared-agent-service.service';

describe('SharedAgentServiceService', () => {
  let service: SharedAgentServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedAgentServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
