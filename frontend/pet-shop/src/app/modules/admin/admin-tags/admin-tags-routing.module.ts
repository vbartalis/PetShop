import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminAuthGuard } from '@app/guard/admin-auth.guard';
import { TagFormComponent } from './tag-form.component';
import { TagListComponent } from './tag-list.component';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [AdminAuthGuard],
    data: { title: 'tags', breadcrumb: 'Tags' },
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'list' },
      {
        path: 'list',
        component: TagListComponent,
      },
      {
        path: 'add',
        component: TagFormComponent,
        data: {
          title: 'Add Tag',
          breadcrumb: 'Add Tags',
        },
      },
      {
        path: 'edit/:id',
        component: TagFormComponent,
        data: {
          title: 'Edit Tag',
          breadcrumb: 'Edit Tags',
        },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminTagsRoutingModule {}
