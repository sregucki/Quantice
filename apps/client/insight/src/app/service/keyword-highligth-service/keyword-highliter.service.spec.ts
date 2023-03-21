import {TestBed} from '@angular/core/testing';

import {KeywordHighlightService} from './keyword-highlight.service';

describe('KeywordHighliterService', () => {
  let service: KeywordHighlightService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KeywordHighlightService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
