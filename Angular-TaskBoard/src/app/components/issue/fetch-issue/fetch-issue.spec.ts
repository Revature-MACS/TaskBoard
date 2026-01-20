import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchIssue } from './fetch-issue';

describe('FetchIssue', () => {
  let component: FetchIssue;
  let fixture: ComponentFixture<FetchIssue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FetchIssue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FetchIssue);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
