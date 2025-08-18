import { Lesson, Option } from './interfaces';

export enum LogDialogMode {
  LogIn = 'Přihlášení:',
  New = 'Nový uživatel:',
  Edit = 'Změna uživatele:',
  Delete = 'Smazat uživatele',
}

export const PRACTICE_COUNT_OPTIONS: Option[] = [
  { label: '10', value: 10 },
  { label: '20', value: 20 },
  {
    label: '40',
    value: 40,
  },
];

export const DEMO_USER = {
  appUserName: 'Demo',
  password: 'P@ssword123',
};

export const ACTION_OPTIONS = [
  { label: 'Uprav', value: 'Edit' },
  { label: 'Smaž', value: 'Delete' },
];

export const SHARED_OPTIONS = [
  { label: 'Ano', value: true },
  { label: 'Ne', value: false },
];

export const WORD_OPTIONS = ['0 - 10', '11 - 20', '> 20'];

export const SCORE_OPTIONS = ['< 0', '0', '> 0'];

export const LESSONS: Lesson[] = [
  {
    id: '84d4a0a7-9b8d-4a50-a7ef-669757e13d05',
    lessonName: 'Verbs',
    description: 'Common verbs',
    shared: false,
    language: 'Grammar',
    score: 2,
    wordCount: 11,
    status: 'own',
  },
  {
    id: '28a19f3b-c720-4ba5-8a5f-4497e8ba933a',
    lessonName: 'Verbs',
    description: 'Common verbs',
    shared: false,
    language: 'Basics',
    score: 0,
    wordCount: 5,
    status: 'own',
  },
  {
    id: 'f297d7b0-b50f-4e77-93f3-44ccd143b94e',
    lessonName: 'Verbs',
    description: 'Common verbs',
    shared: false,
    language: 'Grammar',
    score: null,
    wordCount: 18,
    status: 'foreign',
  },
  {
    id: 'a8a10734-c34f-495e-b923-961e1387e982',
    lessonName: 'Verbs',
    description: 'Common verbs',
    shared: false,
    language: 'Speaking',
    score: -3,
    wordCount: 23,
    status: 'own',
  },
  {
    id: 'ee2ad74c-8194-40fe-ac9a-7cc8c535acaf',
    lessonName: 'Verbs',
    description: 'Common verbs',
    shared: false,
    language: 'Vocabulary',
    score: null,
    wordCount: 47,
    status: 'foreign',
  },
  {
    id: '68852ef0-1ac1-4306-a558-52e2b44be342',
    lessonName: 'Travel',
    description: 'V lekci jsou běžná podstatná jména',
    shared: true,
    language: 'Travel',
    score: -1,
    wordCount: 60,
    status: 'own',
  },
];

export const LESSON_EXAMPLE = {
  id: '68852ef0-1ac1-4306-a558-52e2b44be342',
  lessonName: 'Travel',
  description: 'V lekci jsou běžná podstatná jména',
  shared: true,
  language: 'Travel',
  score: -1,
  wordCount: 60,
  status: 'own',
};

export const MESSAGES = {
  invalidInput: 'Chybný vstup',
  unAuthorizedAccessToLesson: 'K lekci nemáte přístup',
  lessonNotFound: 'Lekce nenalezena',
  userNotFound: 'Uživatel nenalezen',
  lessonConflict: 'Lekce se stejným názvem už existuje',
  userConflict: 'Uživatel se stejným názvem už existuje',
};
