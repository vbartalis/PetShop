import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostPageCriteria } from '@data/api/page-criteria';
import { PostSearchCriteria } from '@data/api/search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
import { Tag } from '@data/model/tag.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { TagDataService } from '@data/service/tag-data.service';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { NgbActiveModal, NgbModal, NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { Observable, OperatorFunction } from 'rxjs';
import { concatMap, debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

@Component({
  selector: 'app-market',
  templateUrl: './market.component.html',
  styles: [],
})
export class MarketComponent implements OnInit {
  postPage: PostPage;

  postPageCriteria: PostPageCriteria = new PostPageCriteria(1, 12);
  postSearchCriteria: PostSearchCriteria = new PostSearchCriteria();

  faTimes = faTimes;

  form: FormGroup;

  tags: Tag[];
  searchTags: Tag[] = [];

  constructor(
    private formBuilder: FormBuilder,
    public tagDataService: TagDataService,
    private postDataService: PostDataService,
    private postImageDataService: PostImageDataService,
    private datePipe: DatePipe,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modalService: NgbModal
  ) {
    this.form = this.formBuilder.group({
      tagsArray: new FormArray([]),
      typeaheadTag: '',
    });
  }

  ngOnInit(): void {
    this.getPostPageFromService();
    this.getTags();
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

  getTags() {
    this.tagDataService.getTags().subscribe((tags: Tag[]) => {
      this.tags = tags;
    });
  }

  onSubmit() {
    this.postSearchCriteria.tagIds = [];
    if (this.searchTags.length > 0) {
      this.searchTags.forEach((tag) => {
        this.postSearchCriteria.tagIds?.push(tag.id);
      });
    }
    console.log(this.postSearchCriteria);
    this.getPostPageFromService();
  }

  getPostLink(post: Post): string {
    return this.router.createUrlTree([post.id], { relativeTo: this.activeRoute.parent }).toString();
  }

  formatter = (tag: Tag) => tag.name;

  search: OperatorFunction<string, readonly { id: any; name: any }[]> = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map((term) => this.tags.filter((tag) => new RegExp(term, 'mi').test(tag.name)).slice(0, 10))
    );

  selectTypeaheadTag($event: NgbTypeaheadSelectItemEvent<any>, input: HTMLInputElement): void {
    $event.preventDefault();
    const tag = $event.item;

    // if tag is not already in post then push it
    if (!this.searchTags.some((t) => t.id === tag.id)) {
      this.searchTags.push(tag);
    }
    input.value = '';
  }

  removeTag(tag: Tag): void {
    this.searchTags = this.searchTags.filter((t) => t.id !== tag.id);
  }
}
