import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserAuthGuard } from '@app/guard/user-auth.guard';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostImageComponent } from './components/post-image/post-image.component';
import { PostListComponent } from './post-list.component';
import { PostComponent } from './post.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [UserAuthGuard],
    data: { title: 'Posts', breadcrumb: 'Posts' },
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'list' },
      {
        path: 'list',
        component: PostListComponent,
      },
      {
        path: 'view/:id',
        component: PostComponent,
        data: {
          title: 'View Post',
          breadcrumb: 'View Post',
        },
      },
      {
        path: 'add',
        component: PostFormComponent,
        data: {
          title: 'Add Post',
          breadcrumb: 'Add Post',
        },
      },
      {
        path: 'edit/:id',
        component: PostFormComponent,
        data: {
          title: 'Edit Post',
          breadcrumb: 'Edit Post',
        },
      },
      {
        path: 'image/:id',
        component: PostImageComponent,
        data: {
          title: 'Edit PostImage',
          breadcrumb: 'Edit PostImage',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserPostsRoutingModule {}
