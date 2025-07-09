import { Routes } from '@angular/router';
import { LessonsOverviewComponent } from './main-views/lessons-overview/lessons-overview.component';
import { WordsOverviewComponent } from './main-views/words-overview/words-overview.component';
import { PracticeSessionComponent } from './main-views/practice-session/practice-session.component';

export const routes: Routes = [
  { path: 'lessons', component: LessonsOverviewComponent }, // default
  { path: 'lesson/:lessonName', component: WordsOverviewComponent },
  { path: 'practice/:lessonName', component: PracticeSessionComponent },
  { path: '**', redirectTo: 'lessons' },
];
