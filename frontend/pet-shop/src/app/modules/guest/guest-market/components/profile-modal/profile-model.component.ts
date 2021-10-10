import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { ProfileDataService } from '@data/service/profile-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile-modal',
  templateUrl: './profile-modal.component.html',
  styles: [],
})
export class ProfileModalComponent implements OnInit {
  @Input()
  profile: Profile;
  profileImageSrc: string;

  constructor(
    private profileDataService: ProfileDataService,
    private datePipe: DatePipe,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.getProfile();
  }

  public getProfile() {
    this.profileDataService.getProfileById(this.profile.id).subscribe((profile) => (this.profile = profile));
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }
}
