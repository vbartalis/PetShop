import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostPageCriteria } from '@data/api/page-criteria';
import { PostSearchCriteria } from '@data/api/search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styles: [],
})
export class MarketComponent implements OnInit {
  postPage: PostPage;

  postPageCriteria: PostPageCriteria = new PostPageCriteria(1, 12);
  postSearchCriteria: PostSearchCriteria = new PostSearchCriteria();

  constructor(
    private postDataService: PostDataService,
    private postImageDataService: PostImageDataService,
    private datePipe: DatePipe,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getPostPageFromService();
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  getPostPage(): void {
    this.getPostPageFromService();
    window.scrollTo(0, 0);
  }

  getPostPageFromService(): void {
    this.postDataService.getAll(this.postPageCriteria, this.postSearchCriteria).subscribe((pageable: PostPage) => {
      this.postPage = pageable;

      this.postPage.content.forEach((post) => {
        this.getPostImage(post);
      });
    });
  }

  getPostImage(post: Post): void {
    this.postImageDataService.getPostImageById(post.postImageId!).subscribe((postImageSrc: string) => {
      post.postImageSrc = postImageSrc;
    });
  }

  openPost(post: Post) {
    this.router.navigate([post.id], { relativeTo: this.activeRoute.parent });
  }
}
