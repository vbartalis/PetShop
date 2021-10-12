import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';

export class PasswordValidator {
  static passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password: string = (control as FormGroup).controls['password'].value;
    const confirmPassword: string = (control as FormGroup).controls['confirmPassword'].value;
    // compare is the password math
    if (password !== confirmPassword) {
      // if they don't match, set an error in our confirmPassword form control
      (control as FormGroup).controls['confirmPassword'].setErrors({ NoPassswordMatch: true });
      return { NoPassswordMatch: true };
    }
    // control.get('confirmPassword').setErrors(null);
    return null;
  }
}
