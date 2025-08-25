import { Country, Option } from './interfaces';

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

export const COUNTRIES: Country[] = [
  {
    label: 'anglicky',
    value: 'anglicky',
    codeDeepL: 'EN-GB',
    fileName: 'gb.webp',
  },
  { label: 'německy', value: 'německy', codeDeepL: 'DE', fileName: 'de.webp' },
  {
    label: 'španělsky',
    value: 'španělsky',
    codeDeepL: 'ES',
    fileName: 'es.webp',
  },
  {
    label: 'fransouzsky',
    value: 'fransouzsky',
    codeDeepL: 'FR',
    fileName: 'fr.webp',
  },
];

export const MESSAGES = {
  invalidInput: 'Chybný vstup',
  unAuthorizedAccessToLesson: 'K lekci nemáte přístup',
  unAuthorizedAccess: 'Nemáte potřebná oprávnění',
  lessonNotFound: 'Lekce nenalezena',
  userNotFound: 'Uživatel nenalezen',
  lessonConflict: 'Lekce se stejným názvem už existuje',
  userConflict: 'Uživatel se stejným názvem už existuje',
};
