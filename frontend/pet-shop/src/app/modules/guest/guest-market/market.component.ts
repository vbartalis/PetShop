import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PostPageCriteria } from '@data/api/post-page-criteria';
import { PostSearchCriteria } from '@data/api/post-search-criteria';
import { PostPage } from '@data/model/post-page';
import { PostDataService } from '@data/service/post-data.service';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styles: [],
})
export class MarketComponent implements OnInit {
  postPage: PostPage | undefined;

  postPageCriteria: PostPageCriteria = new PostPageCriteria(0, 10);
  postSearchCriteria: PostSearchCriteria = new PostSearchCriteria();

  constructor(private postDataService: PostDataService, private datePipe: DatePipe) {}

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
    this.postDataService
      .getAll(this.postPageCriteria, this.postSearchCriteria)
      .subscribe((pageable) => (this.postPage = pageable));
  }
}
