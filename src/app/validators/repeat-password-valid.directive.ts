import { Directive, Input } from '@angular/core';
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms';

@Directive({
  selector: '[repeatPasswordValid]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: RepeatPasswordValidDirective,
      multi: true,
    },
  ],
})
export class RepeatPasswordValidDirective implements Validator {
  @Input('repeatPasswordValid') passwordInput = '';

  validate(control: AbstractControl): ValidationErrors | null {
    const repeat = control.value ?? '';
    if (!this.passwordInput) return null;
    console.log(this.passwordInput);
    console.log(repeat);

    if (repeat.length < 8 || this.passwordInput.length < 8) return null;
    console.log(
      repeat === this.passwordInput ? null : { repeatPasswordValid: true },
    );
    return repeat === this.passwordInput ? null : { repeatPasswordValid: true };
  }
}
