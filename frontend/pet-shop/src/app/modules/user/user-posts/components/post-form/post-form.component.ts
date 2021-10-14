import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '@data/model/post.model';
import { Tag } from '@data/model/tag.model';
import { PostDataService } from '@data/service/post-data.service';
import { TagDataService } from '@data/service/tag-data.service';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { NgbTypeaheadSelectItemEvent } from '@ng-bootstrap/ng-bootstrap';
import { clone, isEqual } from 'lodash';
import { Observable, OperatorFunction } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { BlankValidator } from '../../validator/blank.validator';

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styles: [],
})
export class PostFormComponent implements OnInit {
  faTimes = faTimes;

  form: FormGroup;
  id: number;
  tags: Tag[];
  post: Post = new Post(null!, null!, null!, null!, null!, null!, null!);
  postTags: Tag[] = [];
  mode: string;
  submitted: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private postDataService: PostDataService,
    private tagDataService: TagDataService,
    private activeRoute: ActivatedRoute,
    private router: Router
  ) {
    this.submitted = false;
    this.mode = '';
    this.form = this.formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(50), BlankValidator.noBlank]],
      description: ['', [Validators.required, Validators.maxLength(255), BlankValidator.noBlank]],
      isPublic: [false, Validators.required],
      tagsArray: new FormArray([]),
      tag: '',
    });
  }

  ngOnInit(): void {
    this.activeRoute.paramMap.subscribe((params) => {
      if (params.get('id')) {
        this.id = Number(params.get('id'));
        this.postDataService.getPostById(this.id).subscribe((post) => {
          this.post = post;
          this.mode = 'update';
          this.setPostToForm();
        });
      } else {
        this.mode = 'create';
        this.post = new Post(null!, null!, null!, null!, null!, null!, []);
      }
    });

    this.tagDataService.getTags().subscribe((tags: Tag[]) => {
      this.tags = tags;
    });
  }

  get tagsArray(): FormArray {
    return this.form.get('tagsArray') as FormArray;
  }

  setPostToForm(): void {
    if (this.post.title) this.form.controls['title'].setValue(this.post.title);
    if (this.post.description) this.form.controls['description'].setValue(this.post.description);
    if (this.post.isPublic) this.form.controls['isPublic'].setValue(this.post.isPublic);

    this.postTags = clone(this.post.tags);

    this.tagsArray.clear();
    this.postTags.forEach((tag: Tag, i: number) => {
      this.tagsArray.push(
        this.formBuilder.group({
          id: '',
          name: '',
          description: '',
        })
      );
      const group = (this.form.controls['tagsArray'] as FormGroup).controls[i.toString()] as FormGroup;
      group.controls['id'].setValue(tag.id);
      group.controls['name'].setValue(tag.name);
      group.controls['description'].setValue(tag.description);
    });
  }

  setFormToPost(): void {
    this.post.title = this.form.controls['title'].value;
    this.post.description = this.form.controls['description'].value;
    this.post.isPublic = this.form.controls['isPublic'].value;
    this, (this.post.tags = this.postTags);
  }

  disableSubmit(): boolean {
    return this.form.invalid || this.submitted === true || (this.form.pristine && this.isClean());
  }

  disableReset(): boolean {
    return this.submitted === true || (this.form.pristine && this.isClean());
  }

  isClean(): boolean {
    if (isEqual(this.post.tags, this.postTags)) {
      return true;
    } else {
      return false;
    }
  }

  onSubmit(): void {
    this.submitted = true;
    this.setFormToPost();

    if (this.mode === 'create') {
      this.postDataService.createPost(this.post).subscribe(
        (result) => {
          if (result.id) {
            this.router.navigate(['.'], { relativeTo: this.activeRoute.parent });
          } else {
            this.submitted = false;
          }
        },
        () => {
          this.submitted = false;
        }
      );
    } else if (this.mode === 'update') {
      this.postDataService.updatePost(this.post).subscribe(
        (result) => {
          if (result.id) {
            this.router.navigate(['.'], { relativeTo: this.activeRoute.parent });
          } else {
            this.submitted = false;
          }
        },
        () => {
          this.submitted = false;
        }
      );
    } else {
      this.submitted = false;
    }
  }

  resetForm(): void {
    this.setPostToForm();
    this.form.markAsPristine();
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
    if (!this.postTags.some((t) => t.id === tag.id)) {
      this.postTags.push(tag);
    }
    // // if tag is not already in post then push it
    // if (!this.post.tags.some((t) => t.id === tag.id)) {
    //   this.post.tags.push(tag);
    // }
    input.value = '';
  }

  removeTag(tag: Tag): void {
    this.postTags = this.postTags.filter((t) => t.id !== tag.id);
  }

  isValid(field: string): boolean {
    let isValid = false;

    // If the field is not touched and invalid, it is considered as initial loaded form. Thus set as true
    if (this.form.controls[field].touched === false) {
      isValid = true;
    } else if (
      (this.form.controls[field].touched === true && this.form.controls[field].valid === true) ||
      this.form.controls[field].pending
    ) {
      // If the field is touched and valid value, then it is considered as valid.
      isValid = true;
    }

    return isValid;
  }
}
