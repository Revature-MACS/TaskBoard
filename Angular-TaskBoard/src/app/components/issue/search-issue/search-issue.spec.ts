import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchIssue } from './search-issue';

describe('SearchIssue', () => {
  let component: SearchIssue;
  let fixture: ComponentFixture<SearchIssue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchIssue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchIssue);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
