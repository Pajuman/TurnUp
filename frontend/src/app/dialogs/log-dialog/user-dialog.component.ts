import {
  Component,
  input,
  InputSignal,
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
  templateUrl: './user-dialog.component.html',
  styleUrl: './user-dialog.component.scss',
  standalone: true,
})
export class UserDialogComponent {
  public readonly mode: InputSignal<LogDialogMode> = input.required();
  public readonly userId: InputSignal<string> = input.required();
  public readonly currentUserName: InputSignal<string> = input('');

  public userName = '';
  public password = '';
  public newPassword = '';
  public passwordRepeat = '';
  public readonly dialogOutput: OutputEmitterRef<UserDialogOutput> = output();
  protected readonly LogDialogMode = LogDialogMode;
  private readonly form: Signal<NgForm | undefined> = viewChild('form');

  public confirm() {
    const output: UserDialogOutput = {
      action: this.mode(),
      userName: this.userName || this.currentUserName(),
      currentPassword: this.password,
      newPassword: this.newPassword ?? this.password,
    };
    this.dialogOutput.emit(output);
  }

  public cancel() {
    const output: UserDialogOutput = {
      action: this.mode(),
      userName: '',
      currentPassword: '',
      newPassword: '',
    };
    this.dialogOutput.emit(output);
  }

  public resetForm() {
    setTimeout(() => this.form()?.resetForm(), 0);
  }
}
