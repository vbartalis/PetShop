import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '@app/service/authentication.service';
import { GlobalService } from '@app/service/global.service';
import { faHome, faSearch, faInfoCircle, faKey, faAtom, faCog, faWindowClose } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-guest-header',
  templateUrl: './guest-header.component.html',
  styles: [],
})
export class GuestHeaderComponent implements OnInit {
  public isMenuCollapsed = true;
  isUser = false;
  isAdmin = false;
  isLoggedIn = false;

  faHome = faHome;
  faSearch = faSearch;
  faInfo = faInfoCircle;
  faWindowClose = faWindowClose;
  faKey = faKey;
  faAtom = faAtom;
  faCog = faCog;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private globalService: GlobalService
  ) {
    this.isUser = this.globalService.isLoggedInUser();
    this.isAdmin = this.globalService.isLoggedInAdmin();
    this.isLoggedIn = this.globalService.isLoggedIn();
  }

  ngOnInit(): void {}

  logout(): void {
    this.authenticationService.logout();
    window.location.reload();
  }
}
