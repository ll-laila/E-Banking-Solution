import { TestBed } from '@angular/core/testing';

import { SharedClientService } from './shared-client.service';

describe('SharedClientService', () => {
  let service: SharedClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedClientService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
