import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminAuthGuard } from '@app/guard/admin-auth.guard';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { AccountPasswordComponent } from './components/account-password/account-password.component';
import { AccountProfileFormComponent } from './components/account-profile-form/account-profile-form.component';
import { AccountProfileImageComponent } from './components/account-profile-image/account-profile-image.component';
import { AccountComponent } from './components/account/account.component';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostImageComponent } from './components/post-image/post-image.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostViewComponent } from './components/post-view/post-view.component';
import { UserListComponent } from './user-list.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AdminAuthGuard],
    data: { title: 'Users', breadcrumb: 'Users' },
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'list' },
      {
        path: 'list',
        component: UserListComponent,
      },
      {
        path: 'account/:userId',
        data: {
          title: 'User Account',
          breadcrumb: 'User Account',
        },
        children: [
          {
            path: '',
            component: AccountComponent,
          },
          {
            path: 'password',
            component: AccountPasswordComponent,
            data: {
              title: 'Edit Account Password',
              breadcrumb: 'Edit Account Password',
            },
          },
          {
            path: 'details',
            component: AccountDetailsComponent,
            data: {
              title: 'Edit Account Details',
              breadcrumb: 'Edit Account Details',
            },
          },
          {
            path: 'profile',
            component: AccountProfileFormComponent,
            data: {
              title: 'Edit Profile',
              breadcrumb: 'Edit Profile',
            },
          },
          {
            path: 'image',
            component: AccountProfileImageComponent,
            data: {
              title: 'Edit Image',
              breadcrumb: 'Edit Image',
            },
          },
        ],
      },
      {
        path: 'posts/:userId',
        data: {
          Title: 'User Posts',
          breadcrumb: 'User Posts',
        },
        children: [
          {
            path: '',
            component: PostListComponent,
          },
          {
            path: 'view/:postId',
            component: PostViewComponent,
            data: {
              title: 'View Post',
              breadcrumb: 'View Posts',
            },
          },
          {
            path: 'edit/:postId',
            component: PostFormComponent,
            data: {
              title: 'Edit Post',
              breadcrumb: 'Edit Posts',
            },
          },
          {
            path: 'image/:postId',
            component: PostImageComponent,
            data: {
              title: 'Edit PostImage',
              breadcrumb: 'Edit PostImage',
            },
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminUsersRoutingModule {}
