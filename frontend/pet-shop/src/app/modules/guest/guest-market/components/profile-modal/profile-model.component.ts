import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Profile } from '@data/model/profile.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-profile-modal',
  templateUrl: './profile-modal.component.html',
  styles: [],
})
export class ProfileModalComponent implements OnInit {
  @Input()
  profileId: number;
  profile: Profile;
  profileImageSrc: string;

  constructor(
    private profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.profileDataService
      .getProfileById(this.profileId)
      .pipe(
        concatMap((profile: Profile) => {
          this.profile = profile;
          return this.profileImageDataService.getProfileImageById(this.profile.profileImageId!);
        })
      )
      .subscribe((profileImageSrc: string) => (this.profileImageSrc = profileImageSrc));
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }
}
