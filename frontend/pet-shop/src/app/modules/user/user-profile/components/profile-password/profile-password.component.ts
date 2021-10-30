import { Component, OnInit } from '@angular/core';
import { AbstractControlOptions, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { GlobalService } from '@app/service/global.service';
import { User } from '@data/model/user.model';
import { UserDataService } from '@data/service/user-data.service';
import { PasswordValidator } from '@shared/validator/password.validator';

@Component({
  selector: 'app-profile-password',
  templateUrl: './profile-password.component.html',
  styles: [],
})
export class ProfilePasswordComponent implements OnInit {
  form: FormGroup;

  user: User;
  submitted: boolean;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private globalService: GlobalService,
    private userDataService: UserDataService,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) {
    this.submitted = false;
    this.form = this.formBuilder.group(
      {
        password: ['', [Validators.required, Validators.maxLength(30)]],
        confirmPassword: ['', [Validators.required, Validators.maxLength(30)]],
      },
      {
        validator: PasswordValidator.passwordMatchValidator,
      } as AbstractControlOptions
    );
  }

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User) => {
      this.user = user;
    });
    this.error = '';
  }

  resetForm(): void {
    this.form.controls['password'].setValue('');
    this.form.controls['confirmPassword'].setValue('');
    this.form.markAsPristine();
  }

  setFormToUser(): void {
    this.user.password = this.form.controls['password'].value;
  }

  disableSubmit(): boolean {
    return this.form.invalid || this.form.pristine || this.submitted === true;
  }

  disableReset(): boolean {
    return this.form.pristine || this.submitted === true;
  }

  onSubmit(): void {
    this.setFormToUser();
    this.userDataService.updateUserPassword(this.user).subscribe(
      (user: User) => {
        if (user.id) {
          this.router.navigate(['.'], { relativeTo: this.activeRoute.parent });
        } else {
          this.submitted = false;
        }
      },
      (error: any) => {
        this.submitted = false;
        // Validation errors
        if (error.status === 422) {
          this.error = error.error.error;
        }
      }
    );
  }
}
