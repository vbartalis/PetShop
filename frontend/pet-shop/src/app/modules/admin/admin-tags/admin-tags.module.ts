import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminTagsRoutingModule } from './admin-tags-routing.module';
import { TagsComponent } from './tags.component';


@NgModule({
  declarations: [
    TagsComponent
  ],
  imports: [
    CommonModule,
    AdminTagsRoutingModule
  ]
})
export class AdminTagsModule { }
