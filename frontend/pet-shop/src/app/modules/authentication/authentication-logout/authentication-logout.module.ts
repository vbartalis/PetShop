import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationLogoutRoutingModule } from './authentication-logout-routing.module';
import { LogoutComponent } from './logout.component';


@NgModule({
  declarations: [
    LogoutComponent
  ],
  imports: [
    CommonModule,
    AuthenticationLogoutRoutingModule
  ]
})
export class AuthenticationLogoutModule { }
