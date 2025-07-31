import {
  Component,
  input,
  InputSignal,
  OnInit,
  output,
  OutputEmitterRef,
  Signal,
  viewChild,
} from '@angular/core';
import { LogDialogMode } from '../../constants-interfaces/constants';
import { InputText } from 'primeng/inputtext';
import { FormsModule, NgForm } from '@angular/forms';
import { Button } from 'primeng/button';
import { UserDialogOutput } from '../../constants-interfaces/interfaces';
import { PasswordCharValidDirective } from '../../validators/password-char-valid.directive';
import { RepeatPasswordValidDirective } from '../../validators/repeat-password-valid.directive';

@Component({
  selector: 'log-dialog',
  imports: [
    InputText,
    FormsModule,
    Button,
    PasswordCharValidDirective,
    RepeatPasswordValidDirective,
  ],
  templateUrl: './log-dialog.component.html',
  styleUrl: './log-dialog.component.scss',
  standalone: true,
})
export class LogDialogComponent implements OnInit {
  public readonly mode: InputSignal<LogDialogMode> = input.required();
  public readonly userId: InputSignal<string> = input.required();
  public userName = '';
  public password = '';
  public newPassword = '';
  public passwordRepeat = '';
  public readonly dialogOutput: OutputEmitterRef<UserDialogOutput> = output();
  protected readonly LogDialogMode = LogDialogMode;
  private readonly form: Signal<NgForm | undefined> = viewChild('form');

  ngOnInit(): void {}

  public confirm() {
    const password = this.getPassword();

    const output: UserDialogOutput = {
      action: this.mode(),
      userName: this.userName,
      password: password,
    };
    this.dialogOutput.emit(output);
    this.reset();
  }

  public cancel() {
    const output: UserDialogOutput = {
      action: this.mode(),
      userName: '',
      password: '',
    };
    this.dialogOutput.emit(output);
    this.reset();
  }

  public resetForm() {
    this.form()?.reset();
  }

  private getPassword() {
    if (
      this.mode() === LogDialogMode.LogIn ||
      this.mode() === LogDialogMode.Delete
    ) {
      return this.password;
    } else if (this.mode() === LogDialogMode.New) {
      if (this.password === this.passwordRepeat) {
        return this.password;
      } else {
        console.log('špatně heslo');
      }
    } else {
      return this.newPassword ?? this.password;
    }
    return '';
  }

  private reset() {
    setTimeout(() => {
      this.userName = '';
      this.password = '';
      this.newPassword = '';
      this.passwordRepeat = '';
    }, 100);
  }
}
