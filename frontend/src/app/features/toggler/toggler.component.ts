import { Component, inject } from '@angular/core';
import { ToggleSwitch } from 'primeng/toggleswitch';
import { FormsModule } from '@angular/forms';
import { NgClass, NgOptimizedImage } from '@angular/common';
import { StateService } from '../../services/state.service';
import { SelectButton } from 'primeng/selectbutton';
import { PRACTICE_COUNT_OPTIONS } from '../../constants-interfaces/constants';

@Component({
  selector: 'toggler',
  imports: [ToggleSwitch, FormsModule, NgClass, NgOptimizedImage, SelectButton],
  templateUrl: './toggler.component.html',
  styleUrl: './toggler.component.scss',
})
export class TogglerComponent {
  public readonly stateService = inject(StateService);
  protected readonly PRACTICE_COUNT_OPTIONS = PRACTICE_COUNT_OPTIONS;
}
