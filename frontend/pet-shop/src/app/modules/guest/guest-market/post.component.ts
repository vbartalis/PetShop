import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '@data/model/post.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { concatMap } from 'rxjs/operators';
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
    private postImageDataService: PostImageDataService,
    private datePipe: DatePipe,
    private modalService: NgbModal,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params) => {
          const id = Number(params.get('id'));
          return this.postDataService.getPostById(id);
        }),
        concatMap((post: Post) => {
          this.post = post;
          return this.postImageDataService.getPostImageById(this.post.postImageId!);
        })
      )
      .subscribe((postImage: string) => {
        this.postImageSrc = postImage;
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
