import { Component, signal, ViewEncapsulation } from '@angular/core';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';

@Component({
  selector: 'confirm-action-dialog',
  imports: [Button, Dialog, FormsModule],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class ConfirmDialogComponent {
  public visible = signal(false);
  public confirmation$ = new Subject<boolean>();

  public confirm(confirmed: boolean) {
    this.visible.set(false);

    this.confirmation$.next(confirmed);
  }
}
