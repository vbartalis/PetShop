import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

import { GuestMarketRoutingModule } from './guest-market-routing.module';
import { MarketComponent } from './market.component';
import { SharedModule } from '@shared/shared.module';

@NgModule({
  declarations: [MarketComponent],
  imports: [CommonModule, GuestMarketRoutingModule, SharedModule],
  providers: [DatePipe],
})
export class GuestMarketModule {}
