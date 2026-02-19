import { TestBed } from '@angular/core/testing';

import { StoargeService } from './stoarge.service';

describe('StoargeService', () => {
  let service: StoargeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StoargeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
