import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminTagsRoutingModule } from './admin-tags-routing.module';
import { SharedModule } from '@shared/shared.module';
import { TagFormComponent } from './tag-form.component';
import { TagListComponent } from './tag-list.component';
import { TagDeleteComponent } from './components/tag-delete/tag-delete.component';

@NgModule({
  declarations: [TagListComponent, TagFormComponent, TagDeleteComponent],
  imports: [CommonModule, AdminTagsRoutingModule, SharedModule],
})
export class AdminTagsModule {}
