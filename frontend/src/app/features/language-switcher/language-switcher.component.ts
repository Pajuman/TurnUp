import { Component, model, output, OutputEmitterRef } from '@angular/core';
import { ToggleSwitch } from 'primeng/toggleswitch';
import { FormsModule } from '@angular/forms';
import { NgStyle } from '@angular/common';

@Component({
  selector: 'language-switcher',
  imports: [ToggleSwitch, FormsModule, NgStyle],
  templateUrl: './language-switcher.component.html',
  styleUrl: './language-switcher.component.scss',
})
export class LanguageSwitcherComponent {
  public isTranslationFromCz = model(true);
  public emitter: OutputEmitterRef<boolean> = output();

  public emitValue() {
    this.emitter.emit(this.isTranslationFromCz());
  }
}
