import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserAuthGuard } from '@app/guard/user-auth.guard';
import { ProfileFormComponent } from './components/profile-form/profile-form.component';
import { ProfileImageComponent } from './components/profile-image/profile-image.component';
import { ProfilePasswordComponent } from './components/profile-password/profile-password.component';
import { ProfileComponent } from './profile.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [UserAuthGuard],
    data: { title: 'Profile', breadcrumb: 'Profile' },
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'list' },
      {
        path: 'list',
        component: ProfileComponent,
      },
      {
        path: 'password',
        component: ProfilePasswordComponent,
        data: {
          title: 'Edit Password',
          breadcrumb: 'Edit Password',
        },
      },
      {
        path: 'form',
        component: ProfileFormComponent,
        data: {
          title: 'Edit Profile',
          breadcrumb: 'Edit Profile',
        },
      },
      {
        path: 'image',
        component: ProfileImageComponent,
        data: {
          title: 'Edit Profile Image',
          breadcrumb: 'Edit Profile Image',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserProfileRoutingModule {}
