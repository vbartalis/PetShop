import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { ProfileComponent } from './profile.component';
import { SharedModule } from '@shared/shared.module';

@NgModule({
  declarations: [ProfileComponent],
  imports: [CommonModule, UserProfileRoutingModule, SharedModule],
  providers: [DatePipe],
})
export class UserProfileModule {}
