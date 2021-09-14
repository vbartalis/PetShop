import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuestHomeRoutingModule } from './guest-home-routing.module';
import { HomeComponent } from './home.component';


@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
    GuestHomeRoutingModule
  ]
})
export class GuestHomeModule { }
