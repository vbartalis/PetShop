import { Component, Input, OnInit } from '@angular/core';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile-delete-image',
  templateUrl: './profile-delete-image.component.html',
  styles: [],
})
export class ProfileDeleteImageComponent implements OnInit {
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
