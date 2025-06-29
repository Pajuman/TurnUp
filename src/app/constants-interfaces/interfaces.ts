import { LessonDTO, WordDTO } from '../api';

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
  status: 'new' | 'edited' | 'deleted' | null;
}

export interface ActionDialogOutput {
  action: 'New' | 'Edit';
  question: string;
  answer: string;
}

export interface ConfirmDialogOutput {
  item: 'Lesson' | 'Word';
  confirm: boolean;
}
