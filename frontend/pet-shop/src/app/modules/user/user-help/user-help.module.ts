import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserHelpRoutingModule } from './user-help-routing.module';
import { HelpComponent } from './help.component';

@NgModule({
  declarations: [HelpComponent],
  imports: [CommonModule, UserHelpRoutingModule],
})
export class UserHelpModule {}
