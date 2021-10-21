import { Component, Input, OnInit } from '@angular/core';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-account-profile-image-delete',
  templateUrl: './account-profile-image-delete.component.html',
  styles: [],
})
export class AccountProfileImageDeleteComponent implements OnInit {
  submitted: boolean;
  errorMessage: string;

  @Input() profileImageId: number;

  constructor(public activeModal: NgbActiveModal, private profileImageDataService: ProfileImageDataService) {
    this.submitted = false;
  }

  ngOnInit(): void {
    this.errorMessage = '';
  }

  onDelete(): void {
    this.profileImageDataService.deleteProfileImage(this.profileImageId).subscribe(() => {
      this.activeModal.close('success');
    });
  }
}
