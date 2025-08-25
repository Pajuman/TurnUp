import {
  Component,
  inject,
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
import {
  ActionLessonDialogOutput,
  ActionWordDialogOutput,
} from '../../constants-interfaces/interfaces';
import { FormsModule, NgForm } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import {
  COUNTRIES,
  SHARED_OPTIONS,
} from '../../constants-interfaces/constants';
import { NgStyle } from '@angular/common';
import { WordService } from '../../api';
import { first } from 'rxjs';

@Component({
  selector: 'action-dialog',
  imports: [Button, Dialog, InputText, FormsModule, DropdownModule, NgStyle],
  templateUrl: './actions-dialog.component.html',
  styleUrl: './actions-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class ActionsDialogComponent {
  public readonly dataToSave: OutputEmitterRef<
    ActionWordDialogOutput | ActionLessonDialogOutput
  > = output();
  public readonly form: Signal<NgForm | undefined> = viewChild('form');

  public item: WritableSignal<'Lesson' | 'Word'> = signal('Word');
  public action: WritableSignal<'New' | 'Edit'> = signal('New');
  public question: WritableSignal<string> = signal('');
  public answer: WritableSignal<string> = signal('');
  public name: WritableSignal<string> = signal('');
  public visible = signal(false);
  public save = signal(false);
  public lessonName = signal('');
  public description = signal('');
  public shared: WritableSignal<{ label: string; value: boolean } | undefined> =
    signal(undefined);
  public language = signal('');
  protected readonly SHARED_OPTIONS = SHARED_OPTIONS;
  protected readonly COUNTRIES = COUNTRIES;
  private readonly wordService = inject(WordService);

  public close() {
    if (this.save()) {
      if (this.item() === 'Word') {
        this.dataToSave.emit({
          action: this.action(),
          question: this.question(),
          answer: this.answer(),
        });
      } else if (this.item() === 'Lesson') {
        this.dataToSave.emit({
          action: this.action(),
          lessonName: this.lessonName(),
          description: this.description(),
          shared: this.shared()?.value ?? false,
          language: this.language(),
        });
      }
    }
    this.resetInputs();
  }

  public confirm() {
    this.save.set(true);
    this.visible.set(false);
  }

  public translate(deepLCode: string) {
    this.wordService
      .translateWord({
        text: this.question(),
        sourceLang: 'CS',
        targetLang: deepLCode,
      })
      .pipe(first())
      .subscribe((translation) => this.answer.set(translation));
  }

  private resetInputs() {
    this.form()?.resetForm();
    this.question.set('');
    this.answer.set('');
    this.lessonName.set('');
    this.description.set('');
    this.language.set('');
    this.shared.set(undefined);
  }
}
