import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { ProfileComponent } from './profile.component';
import { SharedModule } from '@shared/shared.module';
import { ProfileDeleteImageComponent } from './components/profile-delete-image/profile-delete-image.component';
import { ProfileFormComponent } from './components/profile-form/profile-form.component';
import { ProfileImageComponent } from './components/profile-image/profile-image.component';
import { ProfilePasswordComponent } from './components/profile-password/profile-password.component';

@NgModule({
  declarations: [
    ProfileComponent,
    ProfileFormComponent,
    ProfilePasswordComponent,
    ProfileImageComponent,
    ProfileDeleteImageComponent,
  ],
  imports: [CommonModule, UserProfileRoutingModule, SharedModule],
  providers: [DatePipe],
})
export class UserProfileModule {}
