import {
  Component,
  input,
  InputSignal,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Button } from 'primeng/button';
import { Popover } from 'primeng/popover';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Table, TableModule } from 'primeng/table';
import { NgClass } from '@angular/common';
import { Subject } from 'rxjs';
import {
  scoreOptions,
  wordsOptions,
} from '../../constants-interfaces/constants';

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
export class Filter implements OnInit, OnDestroy {
  public readonly table: InputSignal<Table> = input.required();
  public readonly fieldName: InputSignal<string> = input.required();
  public readonly clearAllFilters: InputSignal<Subject<string>> =
    input.required();

  public isFilterActive = false;
  public selectedOptions: { [key: string]: boolean } = {};
  public selectedOption: string | null = null;
  public options: string[] = [];

  public ngOnInit(): void {
    if (this.fieldName() === 'wordCount') {
      this.options = wordsOptions;
    } else if (this.fieldName() === 'score') {
      this.options = scoreOptions;
    } else {
      this.setOptions();
    }
    this.clearAllFilters().subscribe(() => this.clearFilter());
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
    } else if (this.fieldName() === 'score') {
      switch (this.selectedOption) {
        case '< 0':
          this.table()?.filter([0, 5], this.fieldName(), 'between');
          break;
        case '0':
          this.table()?.filter(0, this.fieldName(), 'equals');
          break;
        case '> 0':
          this.table()?.filter([-5, 0], this.fieldName(), 'between');
          break;
      }
    } else {
      const selectedOptions = Object.entries(this.selectedOptions)
        .filter(([_, checked]) => checked)
        .map(([option]) => option);
      this.table()?.filter(selectedOptions, this.fieldName(), 'in');
    }
    this.isFilterActive = true;
  }

  public clearFilter() {
    this.selectedOptions = {};
    this.selectedOption = null;
    this.table()?.filter(null, this.fieldName(), 'in');
    this.isFilterActive = false;
  }

  public openFilterDialog(event: MouseEvent) {
    event.stopPropagation();
  }

  public ngOnDestroy(): void {
    this.clearAllFilters().unsubscribe();
  }

  private setOptions() {
    this.table().value.forEach((lesson) => {
      if (!this.options.includes(lesson[this.fieldName()])) {
        this.options.push(lesson[this.fieldName()]);
      }
    });
  }
}
