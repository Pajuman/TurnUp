import { Component, inject } from '@angular/core';
import { ToggleSwitch } from 'primeng/toggleswitch';
import { FormsModule } from '@angular/forms';
import { NgClass, NgOptimizedImage } from '@angular/common';
import { LessonService } from '../../services/lesson.service';

@Component({
  selector: 'toggler',
  imports: [ToggleSwitch, FormsModule, NgClass, NgOptimizedImage],
  templateUrl: './toggler.component.html',
  styleUrl: './toggler.component.scss',
})
export class TogglerComponent {
  public readonly lessonService = inject(LessonService);
}
