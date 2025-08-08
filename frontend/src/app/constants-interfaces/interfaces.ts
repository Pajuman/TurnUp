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

export interface Lesson extends Omit<LessonDTO, 'score'> {
  status: 'own' | 'foreign';
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
