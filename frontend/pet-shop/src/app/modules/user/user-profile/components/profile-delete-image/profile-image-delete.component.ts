import { Component, Input, OnInit } from '@angular/core';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-profile-delete-image',
  templateUrl: './profile-image-delete.component.html',
  styles: [],
})
export class ProfileImageDeleteComponent implements OnInit {
  isLoading: boolean;
  errorMessage: string;

  @Input() profileImageId: number;

  constructor(public activeModal: NgbActiveModal, private profileImageDataService: ProfileImageDataService) {
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.errorMessage = '';
  }

  onDelete(): void {
    this.isLoading = true;
    this.profileImageDataService.deleteProfileImage(this.profileImageId).subscribe(() => {
      this.activeModal.close('success');
    });
  }

  disableDeleteButton(): boolean {
    return this.isLoading === true;
  }
}
