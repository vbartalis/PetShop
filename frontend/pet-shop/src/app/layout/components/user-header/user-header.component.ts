import { Component, OnInit } from '@angular/core';
import {
  faHome,
  faNewspaper,
  faPlusCircle,
  faQuestion,
  faUser,
  faUserAstronaut,
  faWindowClose,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-header',
  templateUrl: './user-header.component.html',
  styles: [],
})
export class UserHeaderComponent implements OnInit {
  public isMenuCollapsed = true;
  isAdmin = false;
  isUser = false;

  faPlusCircle = faPlusCircle;
  faHome = faHome;
  faNewspaper = faNewspaper;
  faUser = faUser;
  faQuestion = faQuestion;
  faWindowClose = faWindowClose;
  faUserAstronaut = faUserAstronaut;

  constructor() {
    // private authenticationService: AuthenticationService, // private globalService: GlobalService,
    // this.isUser = this.globalService.isLoggedInUser();
    // this.isAdmin = this.globalService.isLoggedInAdmin();
  }

  ngOnInit(): void {}

  logout(): void {
    // this.authenticationService.logout();
  }
}
