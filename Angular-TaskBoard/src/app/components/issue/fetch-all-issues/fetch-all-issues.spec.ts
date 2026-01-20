import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FetchAllIssues } from './fetch-all-issues';

describe('FetchAllIssues', () => {
  let component: FetchAllIssues;
  let fixture: ComponentFixture<FetchAllIssues>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FetchAllIssues]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FetchAllIssues);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
