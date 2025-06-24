import {
  Component,
  OnInit,
  signal,
  Signal,
  viewChild,
  WritableSignal,
} from '@angular/core';
import { Table, TableModule } from 'primeng/table';
import {
  LESSONS,
  LogDialogMode,
  PRACTICE_COUNT_OPTIONS,
  SwitcherOption,
} from '../constants/constants';
import { Popover } from 'primeng/popover';
import { LogDialogComponent } from '../log-dialog/log-dialog.component';
import { FormsModule } from '@angular/forms';
import { SelectButton } from 'primeng/selectbutton';
import { Lesson, PracticeCountOption } from '../interfaces/interfaces';
import { TogglerComponent } from '../toggler/toggler.component';
import { Tooltip } from 'primeng/tooltip';
import { Filter } from '../filter/filter';
import { Subject } from 'rxjs';

@Component({
  selector: 'lessons-overview',
  imports: [
    TableModule,
    Popover,
    LogDialogComponent,
    FormsModule,
    SelectButton,
    TogglerComponent,
    Tooltip,
    Filter,
  ],
  templateUrl: './lessons-overview.component.html',
  styleUrl: './lessons-overview.component.scss',
  standalone: true,
})
export class LessonsOverviewComponent implements OnInit {
  public lessons: Lesson[] = [];
  public userName = 'Demo';
  public loggedIn = false;
  public logDialogMode: LogDialogMode = LogDialogMode.New;
  public userId = '';
  public searchValue = '';
  public isFilterActive = false;
  public practiceCount: WritableSignal<PracticeCountOption> = signal({
    label: '20',
    value: 20,
  });
  public clearAllFilters$ = new Subject<string>();
  protected readonly LOG_DIALOG_MODE = LogDialogMode;
  protected readonly PRACTICE_COUNT_OPTIONS = PRACTICE_COUNT_OPTIONS;
  protected readonly SWITCHER_OPTION = SwitcherOption;

  private logDialogPopOver: Signal<Popover | undefined> =
    viewChild('logDialogPopOver');

  ngOnInit(): void {
    this.lessons = LESSONS;
  }

  public openDialog(event: Event, mode: LogDialogMode) {
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

  public togglerSwitched(event: any) {
    console.log(event);
  }

  public toDetail(lessonId: string) {
    console.log('jdu do detailu ' + lessonId);
  }

  public practiceLesson(lessonId: string) {
    console.log('zkoušení z ' + lessonId);
  }

  public copyLesson(lessonId: string) {
    console.log('kopíruju ' + lessonId);
  }
}
