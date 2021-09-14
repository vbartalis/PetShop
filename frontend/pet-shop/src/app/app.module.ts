import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import { AuthenticationLayoutComponent } from './layout/authentication-layout.component';
import { GuestLayoutComponent } from './layout/guest-layout.component';
import { UserLayoutComponent } from './layout/user-layout.component';
import { AdminLayoutComponent } from './layout/admin-layout.component';
import { UserHeaderComponent } from './layout/components/user-header/user-header.component';
import { AdminHeaderComponent } from './layout/components/admin-header/admin-header.component';
import { GuestHeaderComponent } from './layout/components/guest-header/guest-header.component';
import { FooterComponent } from './layout/components/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationLayoutComponent,
    GuestLayoutComponent,
    UserLayoutComponent,
    AdminLayoutComponent,
    UserHeaderComponent,
    AdminHeaderComponent,
    GuestHeaderComponent,
    FooterComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, SharedModule, HttpClientModule],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }],
  bootstrap: [AppComponent],
})
export class AppModule {}
