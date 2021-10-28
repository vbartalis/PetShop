import { Component, Input, OnInit } from '@angular/core';
import { TagDataService } from '@data/service/tag-data.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-tag-delete',
  templateUrl: './tag-delete.component.html',
  styles: [],
})
export class TagDeleteComponent implements OnInit {
  submitted: boolean;
  errorMessage: string;

  @Input() tagId: number;

  constructor(public activeModal: NgbActiveModal, private tagDataService: TagDataService) {
    this.submitted = false;
  }

  ngOnInit(): void {
    this.errorMessage = '';
  }

  onDelete(): void {
    this.tagDataService.deleteTag(this.tagId).subscribe(() => {
      this.activeModal.close('success');
    });
  }
}
