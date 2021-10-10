import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { GuestMarketRoutingModule } from './guest-market-routing.module';
import { MarketComponent } from './market.component';
import { SharedModule } from '@shared/shared.module';
import { PostComponent } from './post.component';
import { ProfileModalComponent } from './components/profile-modal/profile-model.component';

@NgModule({
  declarations: [MarketComponent, PostComponent, ProfileModalComponent],
  imports: [CommonModule, GuestMarketRoutingModule, SharedModule],
  providers: [DatePipe],
})
export class GuestMarketModule {}
