import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserPageCriteria } from '@data/api/page-criteria';
import { UserSearchCriteria } from '@data/api/search-criteria';
import { UserPage } from '@data/model/user-page';
import { UserDataService } from '@data/service/user-data.service';
import { faPen, faPlus, faSearch, faUser } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountNewComponent } from './components/account-new/account-new.component';

@Component({
  selector: 'app-users',
  templateUrl: './user-list.component.html',
  styles: [],
})
export class UserListComponent implements OnInit {
  faPen = faPen;
  faPlus = faPlus;
  faUser = faUser;
  faSearch = faSearch;

  userPage: UserPage;

  userPageCriteria: UserPageCriteria = new UserPageCriteria(1, 10);
  userSearchCriteria: UserSearchCriteria = new UserSearchCriteria();

  constructor(private userDataService: UserDataService, private datePipe: DatePipe, private modalService: NgbModal) {}

  ngOnInit(): void {
    this.getUserPageFromService();
  }

  convertDate(date: Date): string {
    return this.datePipe.transform(date, 'yyyy-MM-dd') ?? '??';
  }

  getUserPage(): void {
    this.getUserPageFromService();
    window.scrollTo(0, 0);
  }

  getUserPageFromService(): void {
    this.userDataService
      .getAll(this.userPageCriteria, this.userSearchCriteria)
      .subscribe((pageable) => (this.userPage = pageable));
  }

  openNewAccountModal(): void {
    const modalRef = this.modalService.open(AccountNewComponent);
    modalRef.closed.subscribe((result) => {
      if (result === 'success') {
        this.getUserPageFromService();
      }
    });
  }
}
