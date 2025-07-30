import { Injectable, signal, WritableSignal } from '@angular/core';
import {
  Lesson,
  PracticeCountOption,
  Word,
} from '../constants-interfaces/interfaces';

@Injectable({
  providedIn: 'root',
})
export class StateService {
  public activeLesson: Lesson = {} as Lesson;
  public activeLessonWords: Word[] = [];
  public practiceCount: PracticeCountOption = {
    label: '20',
    value: 20,
  };
  public languageSwitched = false;
  public userId = signal('');
  public lessons: WritableSignal<Lesson[]> = signal([]);
}
