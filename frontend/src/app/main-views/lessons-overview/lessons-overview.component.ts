import {
  Component,
  inject,
  OnInit,
  signal,
  Signal,
  viewChild,
  WritableSignal,
} from '@angular/core';
import { Table, TableModule } from 'primeng/table';
import {
  DEMO_USER,
  LogDialogMode,
  SHARED_OPTIONS,
  WORDS,
} from '../../constants-interfaces/constants';
import { Popover } from 'primeng/popover';
import { LogDialogComponent } from '../../dialogs/log-dialog/log-dialog.component';
import { FormsModule } from '@angular/forms';
import {
  ActionLessonDialogOutput,
  ConfirmDialogOutput,
  Lesson,
  Option,
  UserDialogOutput,
  Word,
} from '../../constants-interfaces/interfaces';
import { TogglerComponent } from '../../features/toggler/toggler.component';
import { Tooltip } from 'primeng/tooltip';
import { Filter } from '../../features/filter/filter';
import { Observable, Subject, switchMap, tap } from 'rxjs';
import { Router } from '@angular/router';
import { StateService } from '../../services/state.service';
import { AppUserDTO, LessonDTO, UserService, WordDTO } from '../../api';
import { Button } from 'primeng/button';
import { ActionDialogComponent } from '../../dialogs/action-dialog/action-dialog.component';
import { ConfirmDialogComponent } from '../../dialogs/confirm-dialog/confirm-dialog.component';
import { ActionsPopoverComponent } from '../../dialogs/actions-popover/actions-popover.component';

@Component({
  selector: 'lessons-overview',
  imports: [
    TableModule,
    Popover,
    FormsModule,
    TogglerComponent,
    Tooltip,
    Filter,
    Button,
    ActionDialogComponent,
    ConfirmDialogComponent,
    ActionsPopoverComponent,
    LogDialogComponent,
  ],
  templateUrl: './lessons-overview.component.html',
  styleUrl: './lessons-overview.component.scss',
  standalone: true,
})
export class LessonsOverviewComponent implements OnInit {
  public userId = signal('');
  public lessons: WritableSignal<Lesson[]> = signal([]);
  public userName = signal('Demo');
  public loggedIn = false;
  public logDialogMode: LogDialogMode = LogDialogMode.New;
  public searchValue = '';
  public isFilterActive = false;
  public clearAllFilters$ = new Subject<string>();
  public lessonService = inject(StateService);
  protected readonly LOG_DIALOG_MODE = LogDialogMode;
  private readonly actionDialog: Signal<ActionDialogComponent | undefined> =
    viewChild('actionDialog');
  private readonly confirmDialog: Signal<ConfirmDialogComponent | undefined> =
    viewChild('confirmDialog');
  private logDialogPopOver: Signal<Popover | undefined> =
    viewChild('logDialogPopOver');
  private router = inject(Router);
  private userService = inject(UserService);
  private stateService = inject(StateService);
  private editedLesson?: Lesson;

  ngOnInit(): void {
    this.userId = this.stateService.userId;
    this.lessons = this.stateService.lessons;
    if (!this.userId()) {
      this.userService
        .loginUser(DEMO_USER)
        .pipe(
          tap((userId) => this.stateService.userId.set(userId)),
          switchMap((userId) =>
            this.userService.getLessonsOfLoggedInUser(userId),
          ),
        )
        .subscribe((lessons) => this.setUserLesson(lessons));
    }
  }

  public openUserDialog(event: Event, mode: LogDialogMode) {
    this.logDialogMode = mode;
    this.logDialogPopOver()?.toggle(event);
  }

  public clearAllFilters(lessonTable: Table) {
    this.clearAllFilters$.next('clear');
    const currentSort = {
      order: lessonTable.sortOrder,
      field: lessonTable.sortField,
    };
    lessonTable.clear();
    this.searchValue = '';
    this.isFilterActive = false;
    lessonTable.sort(currentSort);

    if (currentSort['order'] === -1) {
      lessonTable.sort(currentSort);
    }
  }

  public toDetail(lesson: Lesson) {
    this.lessonService.activeLesson = lesson;
    //ToDo BE call to get words
    const wordsDto = WORDS;
    this.lessonService.activeLessonWords = this.getWordsFromWordsDto(wordsDto);

    this.router.navigate(['lesson', lesson.lessonName]);
  }

  public practiceLesson(lesson: Lesson) {
    this.lessonService.activeLesson = lesson;
    console.log(this.lessonService.practiceCount);
    this.router.navigate(['practice', lesson.lessonName]);
  }

  public copyLesson(lessonId: string) {
    console.log('kopíruju ' + lessonId);
  }

  public lessonActionSelected(actionSelected: Option, row: Lesson) {
    this.editedLesson = row;

    if (actionSelected.value === 'Edit') {
      this.openDialog('Edit', row);
    } else if (actionSelected.value === 'Delete') {
      this.confirmDialog()?.visible.set(true);
    }
  }

  public openDialog(
    action: 'New' | 'Edit',
    editedLesson: Lesson | null = null,
  ) {
    if (action === 'Edit' && editedLesson) {
      this.actionDialog()!.lessonName.set(editedLesson.lessonName);
      this.actionDialog()!.description.set(editedLesson.description);
      this.actionDialog()!.shared.set(
        editedLesson.shared ? SHARED_OPTIONS[0] : SHARED_OPTIONS[1],
      );
      this.actionDialog()!.category.set(editedLesson.category);
    }
    this.actionDialog()!.item.set('Lesson');
    this.actionDialog()!.action.set(action);
    this.actionDialog()!.visible.set(true);
  }

  public saveLessonChanges(event: any) {
    const output = event as ActionLessonDialogOutput;

    const newOrEditedLesson = {
      lessonName: output.lessonName,
      description: output.description,
      shared: output.shared,
      category: output.category,
      score: 0,
      status: 'own',
    } as Lesson;
    if (output.action === 'New') {
      this.addLesson(newOrEditedLesson);
    } else if (
      output.action === 'Edit' &&
      (output.lessonName !== this.editedLesson?.lessonName ||
        output.category !== this.editedLesson.category ||
        output.description !== this.editedLesson.description ||
        output.shared !== this.editedLesson.shared)
    ) {
      this.editLesson(newOrEditedLesson);
    }
  }

  public confirmDeletion(confirmDialogOutput: ConfirmDialogOutput) {
    if (confirmDialogOutput.confirm) {
      const index = this.lessons().findIndex(
        (l) => l.id === this.editedLesson?.id,
      );
      this.lessons().splice(index, 1);
    }
  }

  public logDialogOutput(event: UserDialogOutput) {
    if (!event.userName && !event.password) {
      return;
    }
    let userObs$: Observable<string>;
    const user = {
      appUserName: event.userName,
      passwordHash: event.password,
    } as AppUserDTO;
    switch (event.action) {
      case LogDialogMode.New:
        userObs$ = this.userService.createUser(user);
        break;
      case LogDialogMode.LogIn:
        userObs$ = this.userService.loginUser(user);
        break;
      case LogDialogMode.Edit:
        userObs$ = this.userService.updateUser(this.userId(), user);
        break;
    }

    userObs$.subscribe((userId) => {
      this.stateService.userId.set(userId);
      this.userName.set(event.userName ?? '');
      this.loggedIn = true;
    });
  }

  private getWordsFromWordsDto(wordsDto: WordDTO[]) {
    const words: Word[] = [];
    wordsDto.forEach((wordDto) => words.push({ ...wordDto, status: null }));
    return words;
  }

  private addLesson(newLessonData: Lesson) {
    this.stateService.lessons.set([
      ...this.stateService.lessons(),
      newLessonData,
    ]);
  }

  private editLesson(newLessonData: Lesson) {
    if (this.editedLesson) {
      this.editedLesson.lessonName = newLessonData.lessonName;
      this.editedLesson.shared = newLessonData.shared;
      this.editedLesson.category = newLessonData.category;
      this.editedLesson.description = newLessonData.description;
    }
  }

  private setUserLesson(lessonDtos: LessonDTO[]) {
    const lessons: Lesson[] = [];
    lessonDtos.forEach((lessonDto) => {
      const lesson: Lesson = lessonDto as Lesson;
      lesson.status = lessonDto.score < 0 ? 'foreign' : 'own';
      lessons.push(lesson);
    });

    this.stateService.lessons.set(lessons);
  }
}
