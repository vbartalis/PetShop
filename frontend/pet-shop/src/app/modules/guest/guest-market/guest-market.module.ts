import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuestMarketRoutingModule } from './guest-market-routing.module';
import { MarketComponent } from './market.component';


@NgModule({
  declarations: [
    MarketComponent
  ],
  imports: [
    CommonModule,
    GuestMarketRoutingModule
  ]
})
export class GuestMarketModule { }
