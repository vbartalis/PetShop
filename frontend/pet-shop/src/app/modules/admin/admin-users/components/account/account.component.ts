import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { faCalendar } from '@fortawesome/free-regular-svg-icons';
import { faKey, faPen, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { concatMap } from 'rxjs/operators';
import { AccountProfileImageDeleteComponent } from '../account-profile-image-delete/account-profile-image-delete.component';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styles: [],
})
export class AccountComponent implements OnInit {
  faPen = faPen;
  faKey = faKey;
  faCalendar = faCalendar;
  faTrash = faTrash;

  id: number;
  user: User;
  profile: Profile;
  profileImageSrc: string;

  constructor(
    private userDataService: UserDataService,
    private profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,
    private activatedRoute: ActivatedRoute,

    private modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params) => {
          this.id = Number(params.get('userId'));
          return this.userDataService.getUserById(this.id);
        }),
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
    const modalRef = this.modalService.open(AccountProfileImageDeleteComponent);
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
