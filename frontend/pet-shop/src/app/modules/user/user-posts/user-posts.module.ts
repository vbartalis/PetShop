import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { UserPostsRoutingModule } from './user-posts-routing.module';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostImageComponent } from './components/post-image/post-image.component';
import { PostDeleteComponent } from './components/post-delete/post-delete.component';
import { PostListComponent } from './post-list.component';
import { PostComponent } from './post.component';
import { SharedModule } from '@shared/shared.module';

@NgModule({
  declarations: [PostFormComponent, PostImageComponent, PostDeleteComponent, PostListComponent, PostComponent],
  imports: [CommonModule, UserPostsRoutingModule, SharedModule],
  providers: [DatePipe],
})
export class UserPostsModule {}
