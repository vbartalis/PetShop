import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuestHelpRoutingModule } from './guest-help-routing.module';
import { HelpComponent } from './help.component';


@NgModule({
  declarations: [
    HelpComponent
  ],
  imports: [
    CommonModule,
    GuestHelpRoutingModule
  ]
})
export class GuestHelpModule { }
