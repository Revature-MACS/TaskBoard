import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateIssue } from './update-issue';

describe('UpdateIssue', () => {
  let component: UpdateIssue;
  let fixture: ComponentFixture<UpdateIssue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateIssue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateIssue);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
