import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Post } from '@data/model/post.model';
import { PostDataService } from '@data/service/post-data.service';
import { map } from 'lodash';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styles: [],
})
export class PostComponent implements OnInit {
  post: Post;

  constructor(
    private postDataService: PostDataService,
    private datePipe: DatePipe,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getPost();
  }

  getPost() {
    this.activeRoute.params.subscribe((params) => {
      this.postDataService.getPostById(params.get('id')).subscribe((post) => (this.post = post));
    });
  }
}
