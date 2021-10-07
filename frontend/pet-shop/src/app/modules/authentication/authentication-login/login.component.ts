import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '@app/service/authentication.service';
import { GlobalService } from '@app/service/global.service';
import { Credentials } from '@data/model/credentials.model';
import { Authentication } from '@data/model/authentication.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private globalService: GlobalService
  ) {
    // redirect if already logged in
    if (this.globalService.isLoggedInAdmin()) {
      this.router.navigate(['/admin']);
    }
    if (this.globalService.isLoggedInUser()) {
      this.router.navigate(['/user']);
    }
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  // convenience getter for easy access to form fields
  get f(): { [p: string]: AbstractControl } {
    return this.loginForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    const authentication = new Authentication(this.f.username.value, this.f.password.value);
    this.authenticationService
      .login(authentication)
      // .pipe(first())
      .subscribe(
        (credentials: Credentials) => {
          if (credentials.roles.includes('ROLE_USER')) {
            this.router.navigate(['/user']);
          } else if (credentials.roles.includes('ROLE_ADMIN')) {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/']);
          }
        },
        (error) => {
          this.error = JSON.stringify(error.error.message);
          this.loading = false;
        }
      );
  }
}
