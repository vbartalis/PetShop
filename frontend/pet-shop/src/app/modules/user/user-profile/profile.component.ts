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
import { ProfileImageDeleteComponent } from './components/profile-delete-image/profile-image-delete.component';

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
          return this.profileDataService.getProfileById(user.profileId!);
        }),
        concatMap((profile: Profile) => {
          this.profile = profile;
          return this.profileImageDataService.getProfileImageById(profile.id);
        })
      )
      .subscribe((profileImage: string) => {
        this.profileImageSrc = profileImage;
      });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  openDeleteProfileImageModal(profile: Profile): void {
    const modalRef = this.modalService.open(ProfileImageDeleteComponent);
    modalRef.componentInstance.profileImageId = profile.profileImageId;
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getProfileImage();
      }
    });
  }

  getProfileImage(): void {
    this.profileImageDataService.getProfileImageById(this.profile.id).subscribe((image: string) => {
      this.profileImageSrc = image;
    });
  }
}
