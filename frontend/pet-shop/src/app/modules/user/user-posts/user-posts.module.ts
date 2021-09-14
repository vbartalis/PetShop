import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserPostsRoutingModule } from './user-posts-routing.module';
import { PostsComponent } from './posts.component';


@NgModule({
  declarations: [
    PostsComponent
  ],
  imports: [
    CommonModule,
    UserPostsRoutingModule
  ]
})
export class UserPostsModule { }
