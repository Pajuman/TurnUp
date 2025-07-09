import {
  Component,
  output,
  OutputEmitterRef,
  Signal,
  signal,
  viewChild,
  ViewEncapsulation,
  WritableSignal,
} from '@angular/core';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import { InputText } from 'primeng/inputtext';
import { ActionDialogOutput } from '../../constants-interfaces/interfaces';
import { FormsModule, NgForm } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { SHARED_OPTIONS } from '../../constants-interfaces/constants';
import { NgStyle } from '@angular/common';

@Component({
  selector: 'action-dialog',
  imports: [Button, Dialog, InputText, FormsModule, DropdownModule, NgStyle],
  templateUrl: './action-dialog.component.html',
  styleUrl: './action-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class ActionDialogComponent {
  public readonly dataToSave: OutputEmitterRef<ActionDialogOutput> = output();
  public readonly form: Signal<NgForm | undefined> = viewChild('form');

  public item: WritableSignal<'Lesson' | 'Word'> = signal('Word');
  public action: WritableSignal<'New' | 'Edit'> = signal('New');
  public question: WritableSignal<string> = signal('');
  public answer: WritableSignal<string> = signal('');
  public name: WritableSignal<string> = signal('');
  public visible = signal(false);
  public save = signal(false);

  public lessonName = '';
  public description = '';
  public shared?: { label: string; value: boolean };
  public category = '';
  protected readonly SHARED_OPTIONS = SHARED_OPTIONS;

  close() {
    if (this.save()) {
      this.dataToSave.emit({
        action: this.action(),
        question: this.question(),
        answer: this.answer(),
      });
    }
    this.form()?.resetForm();
    this.question.set('');
    this.answer.set('');
    this.lessonName = '';
    this.description = '';
    this.category = '';
    this.shared = undefined;
  }
}
