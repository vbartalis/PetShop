import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '@app/service/authentication.service';
import { GlobalService } from '@app/service/global.service';
import { faUsers, faTags, faAtom, faHome, faNewspaper, faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styles: [],
})
export class AdminHeaderComponent implements OnInit {
  public isMenuCollapsed = true;
  isAdmin = false;
  isUser = false;

  faUsers = faUsers;
  faTags = faTags;
  faAtom = faAtom;
  faWindowClose = faWindowClose;

  constructor(private authenticationService: AuthenticationService, private globalService: GlobalService) {
    this.isUser = this.globalService.isLoggedInUser();
    this.isAdmin = this.globalService.isLoggedInAdmin();
  }

  ngOnInit(): void {}

  logout(): void {
    this.authenticationService.logout();
  }
}
