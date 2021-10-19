import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { AdminUsersRoutingModule } from './admin-users-routing.module';

import { AccountComponent } from './components/account/account.component';
import { AccountNewComponent } from './components/account-new/account-new.component';
import { AccountPasswordComponent } from './components/account-password/account-password.component';
import { AccountProfileFormComponent } from './components/account-profile-form/account-profile-form.component';
import { AccountProfileImageComponent } from './components/account-profile-image/account-profile-image.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { AccountProfileImageDeleteComponent } from './components/account-profile-image-delete/account-profile-image-delete.component';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostImageComponent } from './components/post-image/post-image.component';
import { PostViewComponent } from './components/post-view/post-view.component';
import { UserListComponent } from './user-list.component';

@NgModule({
  declarations: [
    UserListComponent,
    AccountComponent,
    AccountNewComponent,
    AccountPasswordComponent,
    AccountProfileFormComponent,
    AccountProfileImageComponent,
    AccountDetailsComponent,
    AccountProfileImageDeleteComponent,
    PostFormComponent,
    PostListComponent,
    PostImageComponent,
    PostViewComponent,
  ],
  imports: [CommonModule, AdminUsersRoutingModule],
  providers: [DatePipe],
})
export class AdminUsersModule {}
