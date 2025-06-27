import { Component, input, InputSignal, OnInit } from '@angular/core';
import { LogDialogMode } from '../constants/constants';
import { InputText } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Button } from 'primeng/button';

@Component({
  selector: 'log-dialog',
  imports: [InputText, FormsModule, Button],
  templateUrl: './log-dialog.component.html',
  styleUrl: './log-dialog.component.scss',
})
export class LogDialogComponent implements OnInit {
  public readonly mode: InputSignal<LogDialogMode> = input.required();
  public readonly userId: InputSignal<string> = input.required();
  public readonly userName: InputSignal<string> = input('');

  public title = '';
  public newUserName = '';
  public password = '';
  public newPassword = '';
  public passwordRepeat = '';
  protected readonly LogDialogMode = LogDialogMode;

  ngOnInit(): void {}

  public confirm() {
    console.log('I confirm');
  }

  public cancel() {
    console.log('I cancel');
  }
}
