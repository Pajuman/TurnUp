import { Component, output, OutputEmitterRef } from '@angular/core';
import { Popover } from 'primeng/popover';
import { Listbox } from 'primeng/listbox';
import { ActionOption } from '../interfaces/interfaces';

@Component({
  selector: 'actions-popover',
  imports: [Popover, Listbox],
  templateUrl: './actions-popover.component.html',
  styleUrl: './actions-popover.component.scss',
  standalone: true,
})
export class ActionsPopoverComponent {
  ACTION_OPTIONS: ActionOption[] = [{ label: 'Edit' }, { label: 'Delete' }];
  public readonly actionSelected: OutputEmitterRef<string> = output();

  public optionSelected(label: string) {
    this.actionSelected.emit(label);
  }
}
