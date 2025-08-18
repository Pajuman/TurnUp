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
  MESSAGES,
  SHARED_OPTIONS,
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
import { MessageService } from 'primeng/api';
import { Toast } from 'primeng/toast';
import { HttpErrorResponse } from '@angular/common/http';

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
    Toast,
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
  private readonly messageService = inject(MessageService);

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
    this.lessonService.getWordsByLessonId(this.userId(), lesson.id).subscribe({
      next: (wordsDto) => {
        this.stateService.activeLessonWords =
          this.getWordsFromWordsDto(wordsDto);
        this.router.navigate(['lesson', lesson.lessonName]);
      },
      error: (err: HttpErrorResponse) => {
        let errMessage = '';
        switch (err.status) {
          case 400:
          case 401:
            errMessage = MESSAGES.invalidInput;
            break;
          case 403:
            errMessage = MESSAGES.unAuthorizedAccessToLesson;
            break;
          case 404:
            errMessage = MESSAGES.lessonNotFound;
            break;
        }
        this.showToast('error', errMessage);
      },
    });
  }

  public practiceLesson(lesson: Lesson) {
    this.stateService.activeLesson = lesson;
    this.router.navigate(['practice', lesson.lessonName]);
  }

  public copyLesson(lessonId: string) {
    this.lessonService.copySharedLesson(this.userId(), lessonId).subscribe({
      next: (lessonDto) => {
        const lesson = this.lessonDtoToLesson(lessonDto);
        this.stateService.lessons.set([...this.stateService.lessons(), lesson]);
        this.showToast('success', 'Lekce zkopírována');
      },
      error: (err: HttpErrorResponse) => {
        let errMessage = '';
        switch (err.status) {
          case 400:
          case 401:
            errMessage = MESSAGES.invalidInput;
            break;
          case 403:
            errMessage = MESSAGES.unAuthorizedAccessToLesson;
            break;
          case 404:
            errMessage = MESSAGES.lessonNotFound;
            break;
          case 409:
            errMessage = MESSAGES.lessonConflict;
            break;
        }
        this.showToast('error', errMessage);
      },
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
        .subscribe({
          next: () => {
            this.lessons.set(
              this.lessons().filter((lesson) => lesson.id !== row.id),
            );
            this.editedLesson = undefined;
            this.showToast('success', 'Lekce editována');
          },
          error: (err: HttpErrorResponse) => {
            let errMessage = '';
            switch (err.status) {
              case 400:
              case 401:
                errMessage = MESSAGES.invalidInput;
                break;
              case 409:
                errMessage = MESSAGES.lessonConflict;
                break;
            }
            this.showToast('error', errMessage);
          },
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
        this.userService.createUser(user).subscribe({
          next: (userId) => {
            this.stateService.userId.set(userId);
            this.userName.set(user.appUserName ?? '');
            this.showToast('success', 'Uživatel vytvořen');
          },
          error: (err: HttpErrorResponse) => {
            let errMessage = '';
            switch (err.status) {
              case 400:
                errMessage = MESSAGES.invalidInput;
                break;
              case 409:
                errMessage = MESSAGES.userConflict;
                break;
            }
            this.showToast('error', errMessage);
          },
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
          .subscribe({
            next: () => {
              this.userName.set(user.appUserName ?? '');
              this.showToast('success', 'Uživatel změněn');
            },
            error: (err: HttpErrorResponse) => {
              let errMessage = '';
              switch (err.status) {
                case 400:
                case 401:
                  errMessage = MESSAGES.invalidInput;
                  break;
                case 409:
                  errMessage = MESSAGES.userConflict;
                  break;
              }
              this.showToast('error', errMessage);
            },
          });
        break;
      case LogDialogMode.Delete:
        const deleteUserRequest: DeleteUserRequest = {
          currentPassword: user.password,
        };
        this.userService
          .deleteUser(this.userId(), deleteUserRequest)
          .subscribe({
            next: () => {
              this.logOut();
              this.showToast('success', 'Uživatel smazán');
            },
            error: (err: HttpErrorResponse) => {
              let errMessage = '';
              switch (err.status) {
                case 400:
                case 401:
                  errMessage = MESSAGES.invalidInput;
                  break;
                case 409:
                  errMessage = MESSAGES.userConflict;
                  break;
              }
              this.showToast('error', errMessage);
            },
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
    this.lessonService.createLesson(this.userId(), newLessonDto).subscribe({
      next: (lessonDto) => {
        const lesson = this.lessonDtoToLesson(lessonDto);
        this.stateService.lessons.set([...this.stateService.lessons(), lesson]);
        this.showToast('success', 'Lekce přidána');
      },
      error: (err: HttpErrorResponse) => {
        let errMessage = '';
        switch (err.status) {
          case 400:
          case 401:
            errMessage = MESSAGES.invalidInput;
            break;
          case 409:
            errMessage = MESSAGES.lessonConflict;
            break;
        }
        this.showToast('error', errMessage);
      },
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

    this.lessonService.updateLesson(this.userId(), editedLessonDto).subscribe({
      next: () => {
        this.editedLesson!.lessonName = lessonData.lessonName;
        this.editedLesson!.shared = lessonData.shared;
        this.editedLesson!.language = lessonData.language;
        this.editedLesson!.description = lessonData.description;
        this.showToast('success', 'Lekce změněna');
      },
      error: (err: HttpErrorResponse) => {
        let errMessage = '';
        switch (err.status) {
          case 400:
          case 401:
            errMessage = MESSAGES.invalidInput;
            break;
          case 403:
            errMessage = MESSAGES.unAuthorizedAccessToLesson;
            break;
          case 404:
            errMessage = MESSAGES.lessonNotFound;
            break;
          case 409:
            errMessage = MESSAGES.lessonConflict;
            break;
        }
        this.showToast('error', errMessage);
      },
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
      .subscribe({
        next: (lessons) => {
          this.setUserLessons(lessons);
          this.stateService.userName.set(user.appUserName);
          this.showToast('success', 'Vítej ' + user.appUserName);
        },
        error: (err: HttpErrorResponse) => {
          console.log('olééé');
          console.log(err.status);

          let errMessage = '';
          switch (err.status) {
            case 400:
            case 401:
              errMessage = MESSAGES.invalidInput;
              break;
            case 404:
              errMessage = MESSAGES.userNotFound;
              break;
          }
          this.showToast('error', errMessage);
        },
      });
  }

  private showToast(severity: 'success' | 'error', detail = '') {
    this.messageService.add({
      severity: severity,
      summary: severity === 'success' ? 'Success' : 'Error',
      detail: detail,
      life: 3000,
    });
  }
}
