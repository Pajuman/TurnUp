import { Injectable, signal, WritableSignal } from '@angular/core';
import {
  Lesson,
  PracticeCountOption,
  Word,
} from '../constants-interfaces/interfaces';
import { Popover } from 'primeng/popover';

@Injectable({
  providedIn: 'root',
})
export class StateService {
  public activeLesson?: Lesson;
  public activeLessonWords: Word[] = [];
  public practiceCount: PracticeCountOption = {
    label: '20',
    value: 20,
  };
  public languageSwitched = false;
  public scoreUpdated?: 'yes' | 'no' | 'notOwned';

  public userId = signal('');
  public lessons: WritableSignal<Lesson[]> = signal([]);
  public userName = signal('Demo');

  private openedPopOver?: Popover;

  public closePreviousPopover(event: any, newlyOpenedPopover: Popover) {
    if (this.openedPopOver && this.openedPopOver === newlyOpenedPopover) {
      this.openedPopOver.hide();
      this.openedPopOver = undefined;
    } else if (this.openedPopOver) {
      this.openedPopOver.hide();
      newlyOpenedPopover.show(event);
      this.openedPopOver = newlyOpenedPopover;
    } else {
      newlyOpenedPopover.show(event);
      this.openedPopOver = newlyOpenedPopover;
    }
  }

  public reset() {
    this.userId.set('');
    this.lessons.set([]);
    this.userName.set('');
  }
}
