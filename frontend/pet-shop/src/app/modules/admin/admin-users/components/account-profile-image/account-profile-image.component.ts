import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GlobalService } from '@app/service/global.service';
import { ProfileImage } from '@data/model/profile-image.model';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-account-profile-image',
  templateUrl: './account-profile-image.component.html',
  styles: [],
})
export class AccountProfileImageComponent implements OnInit {
  userId: number;
  user: User;
  profile: Profile;
  profileImage: ProfileImage;
  submitted: boolean;
  errorMessage: string;

  onChange: any;
  file: File | null = null;
  validType: boolean = true;

  allowedExtensions: string[];
  accept: string;

  constructor(
    private userDataService: UserDataService,
    private profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.submitted = false;
    this.allowedExtensions = ['jpg', 'jpeg'];
    this.accept = 'image/jpeg';
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.userDataService
        .getUserById(this.userId)
        .pipe(
          concatMap((user: User) => {
            this.user = user;
            return this.profileDataService.getProfileById(user.profileId!);
          })
        )
        .subscribe((profile: Profile) => {
          this.profile = profile;
          this.profileImage = new ProfileImage(profile.profileImageId!);
        });
    });
    this.errorMessage = '';
  }

  onSave(): void {
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

  disableSave(): boolean {
    return false;
  }

  // todo
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
