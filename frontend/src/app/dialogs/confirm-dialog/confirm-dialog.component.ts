import {
  Component,
  input,
  InputSignal,
  output,
  OutputEmitterRef,
  signal,
  ViewEncapsulation,
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
  public item: InputSignal<'Lesson' | 'Word'> = input.required();
  public visible = signal(false);

  public confirm(confirmed: boolean) {
    this.visible.set(false);
    this.confirmOutput.emit({
      item: this.item(),
      confirm: confirmed,
    });
  }
}
