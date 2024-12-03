import { TestBed } from '@angular/core/testing';

import { SharedInfosService } from './shared-infos.service';

describe('SharedInfosService', () => {
  let service: SharedInfosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedInfosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
