import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MarketComponent } from './market.component';
import { PostComponent } from './post.component';

const routes: Routes = [
  {
    path: '',
    data: { title: 'Market' },
    children: [
      {
        path: 'list',
        component: MarketComponent,
      },
      {
        path: ':id',
        component: PostComponent,
      },
      { path: '', pathMatch: 'full', redirectTo: 'list' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GuestMarketRoutingModule {}
