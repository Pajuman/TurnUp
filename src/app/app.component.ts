import { Component } from '@angular/core';
import { LessonsOverviewComponent } from './lessons-overview/lessons-overview.component';

@Component({
  selector: 'app-root',
  imports: [LessonsOverviewComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  standalone: true,
})
export class AppComponent {
  title = 'turnup';
}
