import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminPostsRoutingModule } from './admin-posts-routing.module';
import { PostsComponent } from './posts.component';


@NgModule({
  declarations: [
    PostsComponent
  ],
  imports: [
    CommonModule,
    AdminPostsRoutingModule
  ]
})
export class AdminPostsModule { }
