import { LessonDTO } from '../api';
import { SwitcherOption } from '../constants/constants';

export interface PracticeCountOption {
  label: string;
  value: number;
}

export interface TogglerEvent {
  switcherOption: SwitcherOption;
  value: boolean;
}

export interface Lesson extends Omit<LessonDTO, 'score'> {
  status: 'own' | 'foreign';
  score: number | null;
}
