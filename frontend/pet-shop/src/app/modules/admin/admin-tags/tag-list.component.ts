import { Component, OnInit } from '@angular/core';
import { Tag } from '@data/model/tag.model';
import { TagDataService } from '@data/service/tag-data.service';
import { faPen, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { TagDeleteComponent } from './components/tag-delete/tag-delete.component';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styles: [],
})
export class TagListComponent implements OnInit {
  faPlus = faPlus;
  faPen = faPen;
  faTrash = faTrash;

  tagsList: Tag[];

  constructor(private tagDataService: TagDataService, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.getTags();
  }

  getTags() {
    this.tagDataService.getTags().subscribe((tagsList: Tag[]) => {
      this.tagsList = tagsList;
    });
  }

  openDeleteTagModal(tag: Tag) {
    const modalRef = this.modalService.open(TagDeleteComponent);
    modalRef.componentInstance.tagId = tag.id;
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getTags();
      }
    });
  }
}
