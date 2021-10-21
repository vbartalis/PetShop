import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '@data/model/post.model';
import { Profile } from '@data/model/profile.model';
import { User } from '@data/model/user.model';
import { PostDataService } from '@data/service/post-data.service';
import { PostImageDataService } from '@data/service/post-image-data.service';
import { ProfileDataService } from '@data/service/profile-data.service';
import { ProfileImageDataService } from '@data/service/profile-image-data.service';
import { UserDataService } from '@data/service/user-data.service';
import { concatMap } from 'rxjs/operators';

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
    public postDataService: PostDataService,
    public postImageDataService: PostImageDataService,
    public userDataService: UserDataService,
    public profileDataService: ProfileDataService,
    private profileImageDataService: ProfileImageDataService,
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
        // concatMap((post: Post) => {
        //   this.post = post;
        //   return this.profileDataService.getProfileByUserId(this.post.userId);
        // }),
        // concatMap((profile: Profile) => {
        //   this.profile = profile;
        //   return this.postImageDataService.getPostImageById(this.post.postImageId);
        // }),
        // concatMap((postImage: string) => {
        //   this.postImageSrc = postImage;
        //   return this.profileImageDataService.getProfileImageById(this.profile.profileImageId);
        // })
      )
      .subscribe((postImage: string) => {
        this.postImageSrc = postImage;
      });
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }
}
