import { Component } from '@angular/core';

@Component({
  selector: 'language-switcher',
  imports: [],
  templateUrl: './language-switcher.component.html',
  styleUrl: './language-switcher.component.scss',
})
export class LanguageSwitcherComponent {
  public isTranslationFromCz = true;
}
