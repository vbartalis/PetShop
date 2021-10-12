import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { GlobalService } from '@app/service/global.service';
import { PostPageCriteria } from '@data/api/post-page-criteria';
import { PostSearchCriteria } from '@data/api/post-search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
import { User } from '@data/model/user.model';
import { PostDataService } from '@data/service/post-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { faEye, faImage, faPen, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { concatMap } from 'rxjs/operators';
import { PostDeleteComponent } from './components/post-delete/post-delete.component';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styles: [],
})
export class PostListComponent implements OnInit {
  faPlus = faPlus;
  faPen = faPen;
  faEye = faEye;
  faImage = faImage;
  faTrash = faTrash;

  user: User;

  postPage: PostPage;

  postPageCriteria: PostPageCriteria = new PostPageCriteria(1, 10);
  postSearchCriteria: PostSearchCriteria = new PostSearchCriteria();

  constructor(
    private globalService: GlobalService,
    private userDataService: UserDataService,
    private postDataService: PostDataService,
    private modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.userDataService
      .getCurrentUser()
      .pipe(
        concatMap((user: User) => {
          this.user = user;
          this.postSearchCriteria.userId = user.id;
          return this.postDataService.getAll(this.postPageCriteria, this.postSearchCriteria);
        })
      )
      .subscribe((pageable) => (this.postPage = pageable));
  }

  convertDate(dateS: Date): string {
    return this.datePipe.transform(dateS, 'yyyy-MM-dd') ?? '??';
  }

  getPostPage(): void {
    this.postDataService
      .getAll(this.postPageCriteria, this.postSearchCriteria)
      .subscribe((pageable) => (this.postPage = pageable));
    window.scrollTo(0, 0);
  }

  openDeletePostModal(post: Post): void {
    const modalRef = this.modalService.open(PostDeleteComponent);
    modalRef.componentInstance.postId = post.id;
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getPostPage();
      }
    });
  }
}
