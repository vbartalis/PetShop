import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { P404Component } from './pages/p404/p404.component';
import { P500Component } from './pages/p500/p500.component';

@NgModule({
  declarations: [P500Component, P404Component],
  imports: [CommonModule],
})
export class StaticModule {}
