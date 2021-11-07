import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '@data/model/post.model';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { concatMap } from 'rxjs/operators';
import { ProfileModalComponent } from '../profile-modal/profile-model.component';

@Component({
  selector: 'app-post-view',
  templateUrl: './post-view.component.html',
  styles: [],
})
export class PostViewComponent implements OnInit {
  post: Post;
  postImageSrc: string;

  user: User;
  profile: Profile;
  profileImageSrc: string;

  constructor(
    private postDataService: PostDataService,
    private postImageDataService: PostImageDataService,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params) => {
          const id = Number(params.get('postId'));
          return this.postDataService.getPostById(id);
        }),
        concatMap((post: Post) => {
          this.post = post;
          return this.postImageDataService.getPostImageById(this.post.postImageId!);
        })
      )
      .subscribe((postImageSrc: string) => {
        this.postImageSrc = postImageSrc;
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
