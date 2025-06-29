import {
  Component,
  output,
  OutputEmitterRef,
  signal,
  WritableSignal,
} from '@angular/core';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { ActionDialogOutput } from '../interfaces/interfaces';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'action-dialog',
  imports: [Button, Dialog, InputText, FormsModule],
  templateUrl: './action-dialog.component.html',
  styleUrl: './action-dialog.component.scss',
})
export class ActionDialogComponent {
  public readonly dataToSave: OutputEmitterRef<ActionDialogOutput> = output();

  public item: WritableSignal<'Lesson' | 'Word'> = signal('Word');
  public action: WritableSignal<'New' | 'Edit'> = signal('New');
  public question: WritableSignal<string> = signal('');
  public answer: WritableSignal<string> = signal('');
  public name: WritableSignal<string> = signal('');
  public visible = signal(false);
  public save = signal(false);

  close() {
    if (this.save()) {
      this.dataToSave.emit({
        action: this.action(),
        question: this.question(),
        answer: this.answer(),
      });
    }
    this.question.set('');
    this.answer.set('');
  }
}
