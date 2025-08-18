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
  Lesson,
  Option,
  UserDialogOutput,
  Word,
} from '../../constants-interfaces/interfaces';
import { TogglerComponent } from '../../features/toggler/toggler.component';
import { Tooltip } from 'primeng/tooltip';
import { Filter } from '../../features/filter/filter';
import { first, Subject, switchMap, tap } from 'rxjs';
import { Router } from '@angular/router';
import { StateService } from '../../services/state.service';
import {
  AppUserDTO,
  DeleteUserRequest,
  LessonDTO,
  LessonService,
  NewLessonDTO,
  UpdateUserRequest,
  UserService,
  WordDTO,
} from '../../api';
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
  public userName = signal('');
  public logDialogMode: LogDialogMode = LogDialogMode.New;
  public searchValue = '';
  public isFilterActive = false;
  public clearAllFilters$ = new Subject<string>();
  public readonly stateService = inject(StateService);
  protected readonly LOG_DIALOG_MODE = LogDialogMode;
  private editedLesson?: Lesson;
  private readonly actionDialog: Signal<ActionDialogComponent | undefined> =
    viewChild('actionDialog');
  private readonly confirmDialog: Signal<ConfirmDialogComponent | undefined> =
    viewChild('confirmDialog');
  private readonly logDialogPopOver: Signal<Popover | undefined> =
    viewChild('logDialogPopOver');
  private readonly router = inject(Router);
  private readonly userService = inject(UserService);
  private readonly lessonService = inject(LessonService);

  ngOnInit(): void {
    this.userId = this.stateService.userId;
    this.lessons = this.stateService.lessons;
    this.userName = this.stateService.userName;
    if (!this.userId()) {
      this.loginAndSetLessons(DEMO_USER);
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
    this.stateService.activeLesson = lesson;
    //ToDo BE call to get words
    const wordsDto = WORDS;
    this.stateService.activeLessonWords = this.getWordsFromWordsDto(wordsDto);

    this.router.navigate(['lesson', lesson.lessonName]);
  }

  public practiceLesson(lesson: Lesson) {
    this.stateService.activeLesson = lesson;
    this.router.navigate(['practice', lesson.lessonName]);
  }

  public copyLesson(lessonId: string) {
    this.lessonService
      .copySharedLesson(this.userId(), lessonId)
      .subscribe((lessonDto) => {
        const lesson = this.lessonDtoToLesson(lessonDto);
        this.stateService.lessons.set([...this.stateService.lessons(), lesson]);
      });
  }

  public lessonActionSelected(actionSelected: Option, row: Lesson) {
    this.editedLesson = row;

    if (actionSelected.value === 'Edit') {
      this.openDialog('Edit', row);
    } else if (actionSelected.value === 'Delete') {
      this.confirmDialog()?.visible.set(true);
      this.confirmDialog()
        ?.confirmation$.pipe(
          first(),
          switchMap(() =>
            this.lessonService.deleteLesson(this.userId(), row.id),
          ),
        )
        .subscribe(() => {
          this.lessons.set(
            this.lessons().filter((lesson) => lesson.id !== row.id),
          );
          this.editedLesson = undefined;
        });
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
      this.actionDialog()!.language.set(editedLesson.language);
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
      language: output.language,
      score: 0,
      status: 'own',
    } as Lesson;
    if (output.action === 'New') {
      this.addLesson(newOrEditedLesson);
    } else if (
      output.action === 'Edit' &&
      (output.lessonName !== this.editedLesson?.lessonName ||
        output.language !== this.editedLesson.language ||
        output.description !== this.editedLesson.description ||
        output.shared !== this.editedLesson.shared)
    ) {
      this.editLesson(newOrEditedLesson);
    }
  }

  public logDialogOutput(event: UserDialogOutput) {
    if (!event.userName && !event.currentPassword) {
      return;
    }

    const user = {
      appUserName: event.userName ?? this.userName(),
      password: event.currentPassword,
    } as AppUserDTO;

    if (event.action === LogDialogMode.Delete) {
      this.confirmDialog()?.visible.set(true);
      this.confirmDialog()
        ?.confirmation$.pipe(first())
        .subscribe((confirmation) => {
          if (confirmation) {
            this.appUserAction(event.action, user, event.newPassword);
          }
        });
    } else {
      this.appUserAction(event.action, user, event.newPassword);
    }
  }

  public logOut() {
    this.stateService.reset();
  }

  private appUserAction(
    mode: LogDialogMode,
    user: AppUserDTO,
    newPassword: string,
  ) {
    switch (mode) {
      case LogDialogMode.New:
        this.userService.createUser(user).subscribe((userId) => {
          this.stateService.userId.set(userId);
          this.userName.set(user.appUserName ?? '');
        });
        break;
      case LogDialogMode.LogIn:
        this.loginAndSetLessons(user);
        break;
      case LogDialogMode.Edit:
        const updateUserRequest: UpdateUserRequest = {
          appUserName: user.appUserName,
          password: newPassword,
          currentPassword: user.password,
        };
        this.userService
          .updateUser(this.userId(), updateUserRequest)
          .subscribe(() => {
            this.userName.set(user.appUserName ?? '');
          });
        break;
      case LogDialogMode.Delete:
        const deleteUserRequest: DeleteUserRequest = {
          currentPassword: user.password,
        };
        this.userService
          .deleteUser(this.userId(), deleteUserRequest)
          .subscribe(() => {
            this.logOut();
          });
        break;
    }
  }

  private getWordsFromWordsDto(wordsDto: WordDTO[]) {
    const words: Word[] = [];
    wordsDto.forEach((wordDto) => words.push({ ...wordDto, status: null }));
    return words;
  }

  private addLesson(lessonData: Lesson) {
    const newLessonDto: NewLessonDTO = { ...lessonData };
    this.lessonService
      .createLesson(this.userId(), newLessonDto)
      .subscribe((lessonDto) => {
        const lesson = this.lessonDtoToLesson(lessonDto);
        this.stateService.lessons.set([...this.stateService.lessons(), lesson]);
      });
  }

  private editLesson(lessonData: Lesson) {
    if (!this.editedLesson) {
      return;
    }

    const editedLessonDto: LessonDTO = {
      ...this.editedLesson,
      lessonName: lessonData.lessonName,
      shared: lessonData.shared,
      language: lessonData.language,
      description: lessonData.description,
      score: lessonData.score ?? 0,
    };

    this.lessonService
      .updateLesson(this.userId(), editedLessonDto)
      .subscribe(() => {
        this.editedLesson!.lessonName = lessonData.lessonName;
        this.editedLesson!.shared = lessonData.shared;
        this.editedLesson!.language = lessonData.language;
        this.editedLesson!.description = lessonData.description;
      });
  }

  private setUserLessons(lessonDtos: LessonDTO[]) {
    const lessons: Lesson[] = [];
    lessonDtos.forEach((lessonDto) => {
      lessons.push(this.lessonDtoToLesson(lessonDto));
    });

    this.stateService.lessons.set(lessons);
  }

  private lessonDtoToLesson(lessonDto: LessonDTO): Lesson {
    const lesson: Lesson = lessonDto as Lesson;
    lesson.status = lessonDto.score < 0 ? 'foreign' : 'own';

    return lesson;
  }

  private loginAndSetLessons(user: AppUserDTO) {
    this.userService
      .loginUser(user)
      .pipe(
        tap((userId) => this.stateService.userId.set(userId)),
        switchMap((userId) =>
          this.userService.getLessonsOfLoggedInUser(userId),
        ),
      )
      .subscribe((lessons) => {
        this.setUserLessons(lessons);
        this.stateService.userName.set(user.appUserName);
      });
  }
}
