import { Directive } from '@angular/core';
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms';

@Directive({
  selector: '[passwordCharValid]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: PasswordCharValidDirective,
      multi: true,
    },
  ],
})
export class PasswordCharValidDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value || '';
    if (value.length < 8) {
      return null;
    }

    const hasUpperCase = /[A-Z]/.test(value);
    const hasNumber = /\d/.test(value);
    const hasSymbol = /[!@#$%^&*(),.?":{}|<>]/.test(value);

    const isValid = hasUpperCase && hasNumber && hasSymbol;

    return isValid ? null : { strongPassword: true };
  }
}
