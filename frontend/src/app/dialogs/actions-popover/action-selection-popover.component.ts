import { Component, output, OutputEmitterRef } from '@angular/core';
import { Popover } from 'primeng/popover';
import { Listbox } from 'primeng/listbox';
import { ACTION_OPTIONS } from '../../constants-interfaces/constants';
import { Option } from '../../constants-interfaces/interfaces';

@Component({
  selector: 'actions-popover',
  imports: [Popover, Listbox],
  templateUrl: './action-selection-popover.component.html',
  styleUrl: './action-selection-popover.component.scss',
  standalone: true,
})
export class ActionSelectionPopoverComponent {
  public readonly actionSelected: OutputEmitterRef<Option> = output();
  protected readonly ACTION_OPTIONS = ACTION_OPTIONS;

  public optionSelected(label: Option) {
    this.actionSelected.emit(label);
  }
}
