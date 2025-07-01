import {
  Component,
  input,
  InputSignal,
  OnInit,
  output,
  OutputEmitterRef,
} from '@angular/core';
import { LogDialogMode } from '../../constants-interfaces/constants';
import { InputText } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { Button } from 'primeng/button';
import { UserDialogOutput } from '../../constants-interfaces/interfaces';

@Component({
  selector: 'log-dialog',
  imports: [InputText, FormsModule, Button],
  templateUrl: './log-dialog.component.html',
  styleUrl: './log-dialog.component.scss',
  standalone: true,
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
  public readonly dialogOutput: OutputEmitterRef<UserDialogOutput> = output();
  protected readonly LogDialogMode = LogDialogMode;

  ngOnInit(): void {}

  public confirm() {
    const password = this.getPassword();

    const output: UserDialogOutput = {
      action: this.mode(),
      userName: this.newUserName,
      password: password,
    };
    this.dialogOutput.emit(output);
  }

  public cancel() {
    const output: UserDialogOutput = { action: this.mode() };
    this.dialogOutput.emit(output);
  }

  private getPassword() {
    if (this.mode() === LogDialogMode.LogIn) {
      return this.password;
    } else if (this.mode() === LogDialogMode.New) {
      if (this.password === this.passwordRepeat) {
        return this.password;
      } else {
        console.log('špatně heslo');
      }
    } else {
      if (this.newPassword === this.passwordRepeat) {
        return this.newPassword;
      } else {
        console.log('špatně heslo');
      }
    }
    return '';
  }
}
