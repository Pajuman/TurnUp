import { Component, signal, ViewEncapsulation } from '@angular/core';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';

@Component({
  selector: 'confirm-action-dialog',
  imports: [Button, Dialog, FormsModule],
  templateUrl: './confirmation-dialog.component.html',
  styleUrl: './confirmation-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class ConfirmationDialogComponent {
  public visible = signal(false);
  public confirmation$ = new Subject<boolean>();

  public confirm(confirmed: boolean) {
    this.visible.set(false);

    this.confirmation$.next(confirmed);
  }
}
