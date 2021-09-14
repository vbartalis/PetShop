import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationLoginRoutingModule } from './authentication-login-routing.module';
import { LoginComponent } from './login.component';


@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    AuthenticationLoginRoutingModule
  ]
})
export class AuthenticationLoginModule { }
