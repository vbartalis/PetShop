import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthenticationLoginRoutingModule } from './authentication-login-routing.module';
import { LoginComponent } from './login.component';
import { SharedModule } from '@shared/shared.module';

@NgModule({
  declarations: [LoginComponent],
  imports: [CommonModule, AuthenticationLoginRoutingModule, SharedModule],
})
export class AuthenticationLoginModule {}
