import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BreadcrumbModule } from 'xng-breadcrumb';

@NgModule({
  declarations: [],
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgbModule, FontAwesomeModule, BreadcrumbModule],
  exports: [CommonModule, FormsModule, ReactiveFormsModule, NgbModule, FontAwesomeModule, BreadcrumbModule],
})
export class SharedModule {}
