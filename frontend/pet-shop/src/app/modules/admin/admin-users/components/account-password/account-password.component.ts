import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControlOptions } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalService } from '@app/service/global.service';
import { User } from '@data/model/user.model';
import { UserDataService } from '@data/service/user-data.service';
import { PasswordValidator } from '@shared/validator/password.validator';

@Component({
  selector: 'app-account-password',
  templateUrl: './account-password.component.html',
  styles: [],
})
export class AccountPasswordComponent implements OnInit {
  form: FormGroup;

  userId: number;
  user: User;
  submitted: boolean;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private globalService: GlobalService,
    private userDataService: UserDataService,
    private router: Router,
    private activatedRoute: ActivatedRoute
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
    this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.userDataService.getUserById(this.userId).subscribe((user: User) => {
        this.user = user;
      });
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
          this.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
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
