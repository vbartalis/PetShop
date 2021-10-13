import { Component, Input, OnInit } from '@angular/core';
import { PostDataService } from '@data/service/post-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-post-delete',
  templateUrl: './post-delete.component.html',
  styles: [],
})
export class PostDeleteComponent implements OnInit {
  submitted: boolean;
  errorMessage: string;

  @Input() postId: number;

  constructor(public activeModal: NgbActiveModal, private postDataService: PostDataService) {
    this.submitted = false;
  }

  ngOnInit(): void {
    this.errorMessage = '';
  }

  onDelete(): void {
    this.postDataService.deletePost(this.postId).subscribe(() => {
      this.activeModal.close('success');
    });
  }
}
