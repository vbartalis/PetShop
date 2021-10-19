import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminTagsRoutingModule } from './admin-tags-routing.module';
import { TagsComponent } from './tags.component';
import { SharedModule } from '@shared/shared.module';
import { TagFormComponent } from './tag-form.component';
import { TagListComponent } from './tag-list.component';

@NgModule({
  declarations: [TagsComponent, TagListComponent, TagFormComponent],
  imports: [CommonModule, AdminTagsRoutingModule, SharedModule],
})
export class AdminTagsModule {}
