import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-account-profile-form',
  templateUrl: './account-profile-form.component.html',
  styles: [],
})
export class AccountProfileFormComponent implements OnInit {
  form: FormGroup;

  userId: number;
  user: User;
  profile: Profile;
  loading: boolean;

  constructor(
    private userService: UserDataService,
    private profileService: ProfileDataService,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.loading = false;
    this.form = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
      address: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', [Validators.required, Validators.maxLength(500)]],
    });
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.userService
        .getCurrentUser()
        .pipe(
          concatMap((result: User) => {
            this.user = result;
            return this.profileService.getProfileById(this.user.profileId!);
          })
        )
        .subscribe((result) => {
          this.profile = result;
          this.resetForm();
        });
    });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  onSubmit(): void {
    this.loading = true;
    this.setFormToProfile();

    this.profileService.updateProfile(this.profile).subscribe((result: Profile) => {
      if (result.id) {
        this.profile = result;
        this.resetForm();
        this.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
      }
      this.loading = false;
    });
  }

  resetForm(): void {
    this.setProfileToForm();
    this.form.markAsPristine();
  }

  setProfileToForm(): void {
    if (this.profile.name) this.form.controls['name'].setValue(this.profile.name);
    if (this.profile.email) this.form.controls['email'].setValue(this.profile.email);
    if (this.profile.address) this.form.controls['address'].setValue(this.profile.address);
    if (this.profile.description) this.form.controls['description'].setValue(this.profile.description);
  }

  setFormToProfile(): void {
    this.profile.name = this.form.controls['name'].value;
    this.profile.email = this.form.controls['email'].value;
    this.profile.address = this.form.controls['address'].value;
    this.profile.description = this.form.controls['description'].value;
  }

  disableSubmit(): boolean {
    return this.form.invalid || this.form.pristine || this.loading === true;
  }

  disableReset(): boolean {
    return this.form.pristine || this.loading === true;
  }
}
