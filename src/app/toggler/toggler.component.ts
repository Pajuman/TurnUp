import { Component, input, InputSignal, output } from '@angular/core';
import { ToggleSwitch } from 'primeng/toggleswitch';
import { FormsModule } from '@angular/forms';
import { NgClass, NgOptimizedImage } from '@angular/common';
import { SwitcherOption } from '../constants/constants';
import { TogglerEvent } from '../interfaces/interfaces';

@Component({
  selector: 'toggler',
  imports: [ToggleSwitch, FormsModule, NgClass, NgOptimizedImage],
  templateUrl: './toggler.component.html',
  styleUrl: './toggler.component.scss',
})
export class TogglerComponent {
  public readonly type: InputSignal<SwitcherOption> = input.required();
  public readonly togglerSwitchedEvent = output<TogglerEvent>();

  public togglerSwitched(event: any) {
    const eventData: TogglerEvent = {
      switcherOption: this.type(),
      value: event.checked,
    };
    this.togglerSwitchedEvent.emit(eventData);
  }
}
