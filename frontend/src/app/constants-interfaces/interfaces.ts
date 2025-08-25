import { LessonDTO, WordDTO } from '../api';
import { LogDialogMode } from './constants';

export interface PracticeCountOption {
  label: string;
  value: number;
}

export interface Option {
  label: string;
  value: string | number;
}

export interface Country {
  label: string;
  value: string;
  codeDeepL: string;
  fileName: string;
}

export interface Lesson extends Omit<LessonDTO, 'score'> {
  status: 'vlastní' | 'sdílená';
  score: number | null;
}

export interface Word extends WordDTO {
  status: 'edited' | 'deleted' | null;
}

export interface ActionWordDialogOutput {
  action: 'New' | 'Edit';
  question: string;
  answer: string;
}

export interface ActionLessonDialogOutput {
  action: 'New' | 'Edit';
  lessonName: string;
  description: string;
  shared: boolean;
  language: string;
}

export interface UserDialogOutput {
  action: LogDialogMode;
  userName: string;
  currentPassword: string;
  newPassword: string;
}
