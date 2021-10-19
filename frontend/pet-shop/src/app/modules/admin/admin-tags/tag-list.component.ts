import { Component, OnInit } from '@angular/core';
import { Tag } from '@data/model/tag.model';
import { TagDataService } from '@data/service/tag-data.service';
import { faPen, faPlus } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-tag-list',
  templateUrl: './tag-list.component.html',
  styles: [],
})
export class TagListComponent implements OnInit {
  faPlus = faPlus;
  faPen = faPen;

  tagsList: Tag[];

  constructor(private tagDataService: TagDataService) {}

  ngOnInit(): void {
    this.tagDataService.getTags().subscribe((tagsList: Tag[]) => {
      this.tagsList = tagsList;
    });
  }
}
