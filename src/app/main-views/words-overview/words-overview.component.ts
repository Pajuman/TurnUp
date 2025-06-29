import {
  Component,
  inject,
  OnInit,
  Signal,
  signal,
  viewChild,
  WritableSignal,
} from '@angular/core';
import { TableModule } from 'primeng/table';
import { SelectButton } from 'primeng/selectbutton';
import { TogglerComponent } from '../../features/toggler/toggler.component';
import { PRACTICE_COUNT_OPTIONS } from '../../constants-interfaces/constants';
import { FormsModule } from '@angular/forms';
import {
  ActionDialogOutput,
  ConfirmDialogOutput,
  Lesson,
  Option,
  PracticeCountOption,
  Word,
} from '../../constants-interfaces/interfaces';
import { Button } from 'primeng/button';
import { ActionsPopoverComponent } from '../../dialogs/actions-popover/actions-popover.component';
import { ActionDialogComponent } from '../../dialogs/action-dialog/action-dialog.component';
import { ConfirmDialogComponent } from '../../dialogs/confirm-dialog/confirm-dialog.component';
import { LessonService } from '../../services/lesson.service';
import { Router } from '@angular/router';

@Component({
  selector: 'words-overview',
  imports: [
    TableModule,
    SelectButton,
    TogglerComponent,
    FormsModule,
    Button,
    ActionsPopoverComponent,
    ConfirmDialogComponent,
    ActionDialogComponent,
  ],
  templateUrl: './words-overview.component.html',
  styleUrl: './words-overview.component.scss',
  standalone: true,
})
export class WordsOverviewComponent implements OnInit {
  public lesson: WritableSignal<Lesson> = signal({} as Lesson);
  public words: WritableSignal<Word[]> = signal([]);
  public practiceCount: WritableSignal<PracticeCountOption> = signal({
    label: '20',
    value: 20,
  });
  public readonly lessonService = inject(LessonService);
  protected readonly PRACTICE_COUNT_OPTIONS = PRACTICE_COUNT_OPTIONS;
  private editedWord?: Word;
  private readonly actionDialog: Signal<ActionDialogComponent | undefined> =
    viewChild('actionDialog');
  private readonly confirmDialog: Signal<ConfirmDialogComponent | undefined> =
    viewChild('confirmDialog');
  private router = inject(Router);

  public ngOnInit(): void {
    this.lesson.set(this.lessonService.activeLesson);
    this.words.set(this.lessonService.activeLessonWords);
  }

  public practiceLesson() {
    this.lessonService.activeLessonWords = this.words();
    this.router.navigate(['practice', this.lesson().lessonName]);
  }

  public saveWordChanges(event: ActionDialogOutput) {
    if (event.action === 'New') {
      this.addWord(event.question, event.answer);
    } else if (
      event.action === 'Edit' &&
      (event.question !== this.editedWord?.question ||
        event.answer !== this.editedWord.answer)
    ) {
      this.editWord(event.question, event.answer);
    }
  }

  public saveAllChanges() {
    console.log('save all changes');
  }

  public wordActionSelected(actionSelected: Option, row: Word) {
    if (actionSelected.value === 'Edit') {
      this.editedWord = row;
      this.openDialog('Edit', row);
    } else if (actionSelected.value === 'Delete') {
      row.status = 'deleted';
    }
  }

  public openDialog(action: 'New' | 'Edit', currentWord: Word | null = null) {
    if (action === 'Edit' && currentWord) {
      this.actionDialog()!.question.set(currentWord.question);
      this.actionDialog()!.answer.set(currentWord.answer);
    }

    this.actionDialog()!.action.set(action);
    this.actionDialog()!.visible.set(true);
  }

  public back() {
    this.router.navigate(['']);
  }

  public confirmDeletion(confirmDialogOutput: ConfirmDialogOutput) {
    console.log(confirmDialogOutput);
  }

  private addWord(question: string, answer: string) {
    const newWord: Word = {
      id: Math.random().toString(),
      question: question,
      answer: answer,
      score: 0,
      status: 'new',
    };
    this.words.set([newWord, ...this.words()]);
  }

  private editWord(question: string, answer: string) {
    if (this.editedWord) {
      this.editedWord.question = question;
      this.editedWord.answer = answer;
      this.editedWord.status = 'edited';
    }
  }

  private openConfirmDialog() {
    this.confirmDialog()?.item.set('Word');
    this.confirmDialog()?.visible.set(true);
  }
}
