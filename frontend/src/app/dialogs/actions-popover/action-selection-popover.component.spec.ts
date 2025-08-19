import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionSelectionPopoverComponent } from './action-selection-popover.component';

describe('ActionsPopoverComponent', () => {
  let component: ActionSelectionPopoverComponent;
  let fixture: ComponentFixture<ActionSelectionPopoverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionSelectionPopoverComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ActionSelectionPopoverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
