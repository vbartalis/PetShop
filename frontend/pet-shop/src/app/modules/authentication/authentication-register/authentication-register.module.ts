import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationRegisterRoutingModule } from './authentication-register-routing.module';
import { RegisterComponent } from './register.component';


@NgModule({
  declarations: [
    RegisterComponent
  ],
  imports: [
    CommonModule,
    AuthenticationRegisterRoutingModule
  ]
})
export class AuthenticationRegisterModule { }
