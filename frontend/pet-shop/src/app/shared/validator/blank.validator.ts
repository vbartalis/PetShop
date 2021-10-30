import { AbstractControl, ValidationErrors } from '@angular/forms';

export class BlankValidator {
  static noBlank(control: AbstractControl): ValidationErrors | null {
    if (control.value.length !== 0) {
      if (control.value.trim().length === 0) return { noBlank: true };
    }
    return null;
  }
}
