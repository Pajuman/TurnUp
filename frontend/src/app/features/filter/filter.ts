import {
  Component,
  inject,
  input,
  InputSignal,
  OnChanges,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Button } from 'primeng/button';
import { Popover } from 'primeng/popover';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';
import { NgClass } from '@angular/common';
import { Subject } from 'rxjs';
import { WORD_OPTIONS } from '../../constants-interfaces/constants';
import { Lesson } from '../../constants-interfaces/interfaces';
import { StateService } from '../../services/state.service';

@Component({
  selector: 'filter',
  imports: [
    Button,
    Popover,
    ReactiveFormsModule,
    TableModule,
    NgClass,
    FormsModule,
  ],
  templateUrl: './filter.html',
  styleUrl: './filter.scss',
})
export class Filter implements OnInit, OnDestroy, OnChanges {
  public readonly table: InputSignal<Table> = input.required();
  public readonly lessons: InputSignal<Lesson[]> = input.required();
  public readonly fieldName: InputSignal<string> = input.required();
  public readonly clearAllFilters: InputSignal<Subject<string>> =
    input.required();
  public isFilterActive = false;

  public selectedOptions: { [key: string]: boolean } = {};
  public selectedOption: string | null = null;
  public options: string[] = [];

  private readonly stateService = inject(StateService);

  public ngOnInit(): void {
    this.clearAllFilters().subscribe(() => this.clearFilter());
  }

  public ngOnChanges(): void {
    if (this.fieldName() === 'wordCount') {
      this.options = WORD_OPTIONS;
    } else {
      this.setOptions();
    }
  }

  public ngOnDestroy(): void {
    this.clearAllFilters().unsubscribe();
  }

  public applyFilter() {
    if (this.fieldName() === 'wordCount') {
      switch (this.selectedOption) {
        case '0 - 10':
          this.table()?.filter([0, 10], this.fieldName(), 'between');
          break;
        case '11 - 20':
          this.table()?.filter([11, 20], this.fieldName(), 'between');
          break;
        case '> 20':
          this.table()?.filter([21, 1000], this.fieldName(), 'between');
          break;
      }
      if (this.selectedOption) {
        this.isFilterActive = true;
      }
    } else {
      const selectedOptions = Object.entries(this.selectedOptions)
        .filter(([_, checked]) => checked)
        .map(([option]) =>
          this.fieldName() === 'score' ? Number(option) : option,
        );
      this.table()?.filter(selectedOptions, this.fieldName(), 'in');
    }
    if (Object.keys(this.selectedOptions).length > 0) {
      this.isFilterActive = true;
    }
  }

  public clearFilter() {
    this.selectedOptions = {};
    this.selectedOption = null;
    this.table()?.filter(null, this.fieldName(), 'in');
    this.isFilterActive = false;
  }

  public toggleFilterDialog(event: MouseEvent, newlyOpenedPopover: Popover) {
    this.stateService.closePreviousPopover(event, newlyOpenedPopover);
    event.stopPropagation();
  }

  private setOptions() {
    this.lessons().forEach((lesson) => {
      if (
        !this.options.includes(<string>lesson[this.fieldName() as keyof Lesson])
      ) {
        this.options.push(<string>lesson[this.fieldName() as keyof Lesson]);
      }
    });
  }
}
