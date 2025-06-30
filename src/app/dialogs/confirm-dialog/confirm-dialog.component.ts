import {
  Component,
  output,
  OutputEmitterRef,
  signal,
  ViewEncapsulation,
  WritableSignal,
} from '@angular/core';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogOutput } from '../../constants-interfaces/interfaces';

@Component({
  selector: 'confirm-action-dialog',
  imports: [Button, Dialog, FormsModule],
  templateUrl: './confirm-dialog.component.html',
  styleUrl: './confirm-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class ConfirmDialogComponent {
  public readonly confirmOutput: OutputEmitterRef<ConfirmDialogOutput> =
    output();
  public visible = signal(false);
  public item: WritableSignal<'Lesson' | 'Word'> = signal('Word');

  public confirm(confirmed: boolean) {
    this.visible.set(false);
    this.confirmOutput.emit({
      item: this.item(),
      confirm: confirmed,
    });
  }
}
