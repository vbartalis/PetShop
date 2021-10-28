import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminLayoutComponent } from './layout/admin-layout.component';
import { AuthenticationLayoutComponent } from './layout/authentication-layout.component';
import { GuestLayoutComponent } from './layout/guest-layout.component';
import { UserLayoutComponent } from './layout/user-layout.component';
import { P404Component } from './modules/static/pages/p404/p404.component';
import { P500Component } from './modules/static/pages/p500/p500.component';

const routes: Routes = [
  { path: '', redirectTo: 'guest', pathMatch: 'full' },
  {
    path: 'guest',
    component: GuestLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: 'home',
        loadChildren: () => import('@modules/guest/guest-home/guest-home.module').then((m) => m.GuestHomeModule),
      },
      {
        path: 'market',
        loadChildren: () => import('@modules/guest/guest-market/guest-market.module').then((m) => m.GuestMarketModule),
      },
      {
        path: 'help',
        loadChildren: () => import('@modules/guest/guest-help/guest-help.module').then((m) => m.GuestHelpModule),
      },
      { path: '**', component: P404Component, data: { title: '404 - Page not found' } },
    ],
  },
  {
    path: 'user',
    component: UserLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      {
        path: 'home',
        loadChildren: () => import('@modules/user/user-home/user-home.module').then((m) => m.UserHomeModule),
      },
      {
        path: 'posts',
        loadChildren: () => import('@modules/user/user-posts/user-posts.module').then((m) => m.UserPostsModule),
      },
      {
        path: 'profile',
        loadChildren: () => import('@modules/user/user-profile/user-profile.module').then((m) => m.UserProfileModule),
      },
      {
        path: 'help',
        loadChildren: () => import('@modules/user/user-help/user-help.module').then((m) => m.UserHelpModule),
      },
      { path: '**', component: P404Component, data: { title: '404 - Page not found' } },
    ],
  },
  {
    path: 'admin',
    component: AdminLayoutComponent,
    children: [
      { path: '', redirectTo: 'users', pathMatch: 'full' },
      {
        path: 'users',
        loadChildren: () => import('@modules/admin/admin-users/admin-users.module').then((m) => m.AdminUsersModule),
      },
      {
        path: 'tags',
        loadChildren: () => import('@modules/admin/admin-tags/admin-tags.module').then((m) => m.AdminTagsModule),
      },
      { path: '**', component: P404Component, data: { title: '404 - Page not found' } },
    ],
  },
  {
    path: 'authentication',
    component: AuthenticationLayoutComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      {
        path: 'login',
        loadChildren: () =>
          import('@modules/authentication/authentication-login/authentication-login.module').then(
            (m) => m.AuthenticationLoginModule
          ),
      },
      {
        path: 'logout',
        loadChildren: () =>
          import('@modules/authentication/authentication-logout/authentication-logout.module').then(
            (m) => m.AuthenticationLogoutModule
          ),
      },
      {
        path: 'register',
        loadChildren: () =>
          import('@modules/authentication/authentication-register/authentication-register.module').then(
            (m) => m.AuthenticationRegisterModule
          ),
      },
    ],
  },
  { path: '500', component: P500Component, data: { title: '500 - Server error' } },
  { path: '**', component: P404Component, data: { title: '404 - Page not found' } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
