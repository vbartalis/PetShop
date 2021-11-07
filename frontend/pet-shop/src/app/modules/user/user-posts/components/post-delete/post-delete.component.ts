import { Component, Input, OnInit } from '@angular/core';
import { PostDataService } from '@data/service/post-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-post-delete',
  templateUrl: './post-delete.component.html',
  styles: [],
})
export class PostDeleteComponent implements OnInit {
  isLoading: boolean;
  errorMessage: string;

  @Input() postId: number;

  constructor(public activeModal: NgbActiveModal, private postDataService: PostDataService) {
    this.isLoading = false;
  }

  ngOnInit(): void {
    this.errorMessage = '';
  }

  onDelete(): void {
    this.isLoading = true;
    this.postDataService.deletePost(this.postId).subscribe(() => {
      this.activeModal.close('success');
    });
  }

  disableDeleteButton(): boolean {
    return this.isLoading === true;
  }
}
