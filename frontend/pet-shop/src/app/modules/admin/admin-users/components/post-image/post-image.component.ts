import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostImage } from '@data/model/post-image.model';
import { Post } from '@data/model/post.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { concatMap } from 'rxjs/operators';

@Component({
  selector: 'app-post-image',
  templateUrl: './post-image.component.html',
  styles: [],
})
export class PostImageComponent implements OnInit {
  postImage: PostImage;
  submitted: boolean;
  errorMessage: string;

  onChange: any;
  file: File | null = null;
  validType: boolean = true;

  allowedExtensions: string[];
  accept: string;

  constructor(
    private postDataService: PostDataService,
    private postImageDataService: PostImageDataService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.submitted = false;
    this.allowedExtensions = ['jpg', 'jpeg'];
    this.accept = 'image/jpeg';
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(
        concatMap((params) => {
          return this.postDataService.getPostById(Number(params.get('postId')));
        })
      )
      .subscribe((post: Post) => {
        this.postImage = new PostImage(post.postImageId!);
      });
    this.errorMessage = '';
  }

  onSave(): void {
    if (this.file) {
      this.postImageDataService.updatePostImage(this.postImage.id, this.file).subscribe((postImage) => {
        if (postImage.id) {
          this.router.navigate(['.'], { relativeTo: this.activatedRoute.parent });
        } else {
          this.submitted = false;
        }
      });
    }
  }

  // todo
  disableSave(): boolean {
    return false;
  }

  onFileSelected(event: any): void {
    this.file = event.target.files[0];
    this.validType = this.isFileTypeAllowed();
  }

  isFileTypeAllowed(): boolean {
    if (this.allowedExtensions == null || this.allowedExtensions.length === 0) {
      return true;
    }
    let isAllowed = false;
    if (this.file) {
      const fileExt = this.file.name.split('.').pop();
      if (fileExt) {
        this.allowedExtensions.forEach((crtExt) => {
          if (crtExt.trim().toLowerCase() === fileExt.trim().toLowerCase()) {
            isAllowed = true;
            console.log('isAllowed: ' + isAllowed);
          }
        });
      }
    }
    return isAllowed;
  }
}
