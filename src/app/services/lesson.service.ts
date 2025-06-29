import { Injectable } from '@angular/core';
import { Lesson, Word } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root',
})
export class LessonService {
  public activeLesson: Lesson = {} as Lesson;
  public activeLessonWords: Word[] = [];
}
