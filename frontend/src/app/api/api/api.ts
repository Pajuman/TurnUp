export * from './lesson.service';
import { LessonService } from './lesson.service';
export * from './user.service';
import { UserService } from './user.service';
export * from './word.service';
import { WordService } from './word.service';
export const APIS = [LessonService, UserService, WordService];
