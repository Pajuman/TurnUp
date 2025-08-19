import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionsDialogComponent } from './actions-dialog.component';

describe('DialogComponent', () => {
  let component: ActionsDialogComponent;
  let fixture: ComponentFixture<ActionsDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionsDialogComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ActionsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
