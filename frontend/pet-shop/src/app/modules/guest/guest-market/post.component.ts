import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '@data/model/post.model';
import { PostDataService } from '@data/service/post-data.service';
import { ProfileDataService } from '@data/service/profile-data.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProfileModalComponent } from './components/profile-modal/profile-model.component';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styles: [],
})
export class PostComponent implements OnInit {
  post: Post;
  postImageSrc: string;

  constructor(
    private postDataService: PostDataService,
    private profileDataService: ProfileDataService,
    private datePipe: DatePipe,
    private modalService: NgbModal,
    private activeRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getPost();
  }

  getPost() {
    this.activeRoute.params.subscribe((params) => {
      this.postDataService.getPostById(params['id']).subscribe((post) => (this.post = post));
    });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  openProfile(): void {
    const modalref = this.modalService.open(ProfileModalComponent);
    modalref.componentInstance.profileId = this.post.profileId;
  }
}
