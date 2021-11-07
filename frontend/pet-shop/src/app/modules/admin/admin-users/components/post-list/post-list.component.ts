import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostPageCriteria } from '@data/api/page-criteria';
import { PostSearchCriteria } from '@data/api/search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
import { User } from '@data/model/user.model';
import { PostDataService } from '@data/service/post-data.service';
import { faEye, faImage, faPen } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PostFormComponent } from '../post-form/post-form.component';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styles: [],
})
export class PostListComponent implements OnInit {
  faPen = faPen;
  faEye = faEye;
  faImage = faImage;

  userId: number;
  user: User;

  postPage: PostPage;

  postPageCriteria: PostPageCriteria = new PostPageCriteria(1, 10);
  postSearchCriteria: PostSearchCriteria = new PostSearchCriteria();

  constructor(
    private postDataService: PostDataService,
    private activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.postSearchCriteria.userId = this.userId;
      this.getPostPage();
    });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  getPostPage(): void {
    this.postDataService
      .getAll(this.postPageCriteria, this.postSearchCriteria)
      .subscribe((pageable) => (this.postPage = pageable));
    window.scrollTo(0, 0);
  }

  openEditModal(post: Post): void {
    const modalRef = this.modalService.open(PostFormComponent);
    modalRef.componentInstance.post = post;
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getPostPage();
      }
    });
  }
}
