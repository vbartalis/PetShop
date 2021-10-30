import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfileImage } from '@data/model/profile-image.model';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-profile-image',
  templateUrl: './profile-image.component.html',
  styles: [],
})
export class ProfileImageComponent implements OnInit {
  form: FormGroup;

  profileImage: ProfileImage;
  submitted: boolean;
  errorMessage: string;

  onChange: any;
  file: File | null = null;
  validType: boolean = true;

  allowedExtensions: string[];
  accept: string;

  constructor(
    private formBuilder: FormBuilder,
    private userDataService: UserDataService,
    private profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.submitted = false;
    this.allowedExtensions = ['jpg', 'jpeg'];
    this.accept = 'image/jpeg';
    this.form = this.formBuilder.group({
      fileControl: '',
    });
  }

  ngOnInit(): void {
    this.userDataService
      .getCurrentUser()
      .pipe(
        concatMap((user: User) => {
          return this.profileDataService.getProfileById(user.profileId!);
        })
      )
      .subscribe((profile: Profile) => {
        this.profileImage = new ProfileImage(profile.profileImageId!);
      });
    this.errorMessage = '';
  }

  onSubmit(): void {
    if (this.file) {
      this.profileImageDataService.updateProfileImage(this.profileImage.id, this.file).subscribe((profileImage) => {
        if (profileImage.id) {
          this.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
        } else {
          this.submitted = false;
        }
      });
    }
  }

  disableSubmit(): boolean {
    return !this.validType || this.submitted === true || this.form.pristine;
  }

  onFileSelected(event: any): void {
    this.file = event.target.files[0];
    this.validType = this.isFileTypeAllowed();
  }

  isFileTypeAllowed(): boolean {
    if (this.allowedExtensions == null || this.allowedExtensions.length === 0) {
      return true;
    }
    let isAllowed = false;
    if (this.file) {
      const fileExt = this.file.name.split('.').pop();
      if (fileExt) {
        this.allowedExtensions.forEach((crtExt) => {
          if (crtExt.trim().toLowerCase() === fileExt.trim().toLowerCase()) {
            isAllowed = true;
            console.log('isAllowed: ' + isAllowed);
          }
        });
      }
    }
    return isAllowed;
  }
}
