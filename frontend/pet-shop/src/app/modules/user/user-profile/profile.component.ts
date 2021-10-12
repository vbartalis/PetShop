import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from '@app/service/authentication.service';
import { GlobalService } from '@app/service/global.service';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { faKey, faPen, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { concatMap } from 'rxjs/operators';
import { ProfileDeleteImageComponent } from './components/profile-delete-image/profile-delete-image.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styles: [],
})
export class ProfileComponent implements OnInit {
  faKey = faKey;
  faPen = faPen;
  faTrash = faTrash;

  user: User;
  profile: Profile;
  profileImageSrc: string;

  constructor(
    private globalService: GlobalService,
    private userDataService: UserDataService,
    private profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,

    private modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.userDataService
      .getCurrentUser()
      .pipe(
        concatMap((user: User) => {
          this.user = user;
          // console.log('user: ', user);
          return this.profileDataService.getProfileById(user.profileId!);
        }),
        concatMap((profile: Profile) => {
          this.profile = profile;
          // console.log('profile: ', profile);
          return this.profileImageDataService.getProfileImageById(profile.id);
        })
      )
      .subscribe((profileImage: string) => {
        // console.log('image: ', image);
        this.profileImageSrc = profileImage;
      });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  openDeleteProfileImageModal(profile: Profile): void {
    const modalRef = this.modalService.open(ProfileDeleteImageComponent);
    modalRef.componentInstance.profileImageId = profile.profileImageId;
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getProfileImage();
      }
    });
  }

  getProfileImage(): void {
    this.profileImageDataService.getProfileImageById(this.profile.id).subscribe((image: string) => {
      // console.log('image: ', image);
      this.profileImageSrc = image;
    });
  }

  //---------------------------
  // user: User;
  // profile: Profile;
  // form: FormGroup;
  // loading: boolean;

  // constructor(
  //   private userService: UserDataService,
  //   private profileService: ProfileDataService,
  //   private profileImageService: ProfileImageDataService,
  //   private authenticationService: AuthenticationService,
  //   private formBuilder: FormBuilder,
  //   private datePipe: DatePipe
  // ) {
  //   this.loading = false;
  //   this.form = this.formBuilder.group({
  //     name: ['', [Validators.required, Validators.maxLength(50)]],
  //     email: ['', [Validators.required, Validators.maxLength(50), Validators.email]],
  //     address: ['', [Validators.required, Validators.maxLength(100)]],
  //     description: ['', [Validators.required, Validators.maxLength(500)]],
  //   });
  // }

  // ngOnInit(): void {
  //   this.userService
  //     .getCurrentUser()
  //     .pipe(
  //       concatMap((result: User) => {
  //         this.user = result;
  //         return this.profileService.getProfileById(this.user.profileId!);
  //       })
  //     )
  //     .subscribe((result) => {
  //       this.profile = result;
  //       this.resetForm();
  //     });
  // }

  // convertDate(date: Date): string {
  //   return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  // }

  // onSubmit(): void {
  //   this.loading = true;
  //   this.setFormToProfile();

  //   this.profileService.updateProfile(this.profile).subscribe((result) => {
  //     if (result.id) {
  //       this.profile = result;
  //       this.resetForm();
  //     }
  //     this.loading = false;
  //   });
  // }

  // resetForm(): void {
  //   this.setProfileToForm();
  //   this.form.markAsPristine();
  // }

  // setProfileToForm(): void {
  //   if (this.profile.name) this.form.controls['name'].setValue(this.profile.name);
  //   if (this.profile.email) this.form.controls['email'].setValue(this.profile.email);
  //   if (this.profile.address) this.form.controls['address'].setValue(this.profile.address);
  //   if (this.profile.description) this.form.controls['description'].setValue(this.profile.description);
  // }

  // setFormToProfile(): void {
  //   this.profile.name = this.form.controls['name'].value;
  //   this.profile.email = this.form.controls['email'].value;
  //   this.profile.address = this.form.controls['address'].value;
  //   this.profile.description = this.form.controls['description'].value;
  // }

  // // todo
  // disableSubmit(): boolean {
  //   return this.form.invalid || this.form.pristine || this.loading === true;
  // }

  // disableReset(): boolean {
  //   return this.form.pristine || this.loading === true;
  // }
}
