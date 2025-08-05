import {
  Component,
  inject,
  OnDestroy,
  OnInit,
  Signal,
  signal,
  viewChild,
  WritableSignal,
} from '@angular/core';
import { TableModule } from 'primeng/table';
import { TogglerComponent } from '../../features/toggler/toggler.component';
import { FormsModule } from '@angular/forms';
import {
  ActionWordDialogOutput,
  Lesson,
  Option,
  Word,
} from '../../constants-interfaces/interfaces';
import { Button } from 'primeng/button';
import { ActionsPopoverComponent } from '../../dialogs/actions-popover/actions-popover.component';
import { ActionDialogComponent } from '../../dialogs/action-dialog/action-dialog.component';
import { ConfirmDialogComponent } from '../../dialogs/confirm-dialog/confirm-dialog.component';
import { StateService } from '../../services/state.service';
import { Router } from '@angular/router';
import { LessonService } from '../../api';

@Component({
  selector: 'words-overview',
  imports: [
    TableModule,
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
export class WordsOverviewComponent implements OnInit, OnDestroy {
  public lesson: WritableSignal<Lesson> = signal({} as Lesson);
  public words: WritableSignal<Word[]> = signal([]);
  public wordsChanged = false;
  public readonly stateService = inject(StateService);
  private editedWord?: Word;
  private userId = '';
  private readonly actionDialog: Signal<ActionDialogComponent | undefined> =
    viewChild('actionDialog');
  private readonly confirmDialog: Signal<ConfirmDialogComponent | undefined> =
    viewChild('confirmDialog');
  private router = inject(Router);
  private readonly lessonService = inject(LessonService);

  public ngOnInit(): void {
    this.lesson.set(this.stateService.activeLesson);
    this.words.set(this.stateService.activeLessonWords);
    this.userId = this.stateService.userId();
    this.lessonService
      .getWordsByLessonId(this.userId, this.lesson().id)
      .subscribe((words) => {
        this.words.set(words.map((word) => ({ ...word, status: null })));
      });
  }

  public ngOnDestroy(): void {}

  public practiceLesson() {
    this.stateService.activeLessonWords = this.words();
    this.router.navigate(['practice', this.lesson().lessonName]);
  }

  public saveWordChanges(event: any) {
    const output = event as ActionWordDialogOutput;

    if (output.action === 'New') {
      this.addWord(output.question, output.answer);
    } else if (
      output.action === 'Edit' &&
      (output.question !== this.editedWord?.question ||
        output.answer !== this.editedWord.answer)
    ) {
      this.editWord(output.question, output.answer);
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

    this.actionDialog()!.save.set(false);
    this.actionDialog()!.action.set(action);
    this.actionDialog()!.visible.set(true);
  }

  public back() {
    this.router.navigate(['']);
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
    this.confirmDialog()?.visible.set(true);
  }
}
