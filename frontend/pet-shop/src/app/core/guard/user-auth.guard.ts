import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { GlobalService } from '@app/service/global.service';

@Injectable({ providedIn: 'root' })
export class UserAuthGuard implements CanActivate, CanActivateChild {
  constructor(private router: Router, private globalService: GlobalService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.globalService.isLoggedInUser()) {
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(['authentication/login']);
    return false;
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.canActivate(route, state);
  }
}
