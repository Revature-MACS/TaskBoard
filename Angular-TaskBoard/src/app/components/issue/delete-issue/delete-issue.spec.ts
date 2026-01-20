import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteIssue } from './delete-issue';

describe('DeleteIssue', () => {
  let component: DeleteIssue;
  let fixture: ComponentFixture<DeleteIssue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteIssue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteIssue);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
