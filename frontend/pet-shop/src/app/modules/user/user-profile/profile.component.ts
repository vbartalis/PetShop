import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthenticationService } from '@app/service/authentication.service';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styles: [],
})
export class ProfileComponent implements OnInit {
  user: User;
  profile: Profile;
  profileForm: FormGroup;

  constructor(
    private userService: UserDataService,
    private profileService: ProfileDataService,
    private profileImageService: ProfileImageDataService,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe
  ) {
    this.profileForm = this.formBuilder.group({
      nameControl: '',
      emailControl: '',
      addressControl: '',
      descriptionControl: '',
    });
  }

  ngOnInit(): void {
    this.userService
      .getCurrentUser()
      .pipe(
        concatMap((result) => {
          this.user = result;
          return this.profileService.getProfileById(this.user.profile.id);
        })
      )
      .subscribe((result) => {
        this.profile = result;
        this.setUserAndProfileToForm();
      });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  resetInput(): void {}

  submit(event: any): void {
    this.setFormToProfile();
    this.profileService;
  }

  setUserAndProfileToForm(): void {
    if (this.profile.name) this.profileForm.controls['nameControl'].setValue(this.profile.name);
    if (this.profile.email) this.profileForm.controls['emailControl'].setValue(this.profile.email);
    if (this.profile.address) this.profileForm.controls['addressControl'].setValue(this.profile.address);
    if (this.profile.description) this.profileForm.controls['descriptionControl'].setValue(this.profile.description);
  }

  setFormToProfile(): void {
    this.profile.name = this.profileForm.controls['nameControl'].value;
    this.profile.email = this.profileForm.controls['emailControl'].value;
    this.profile.address = this.profileForm.controls['addressControl'].value;
    this.profile.description = this.profileForm.controls['descriptionControl'].value;
  }
}
