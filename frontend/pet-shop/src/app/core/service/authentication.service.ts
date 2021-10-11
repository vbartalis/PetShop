import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiCredentials } from '@data/api/api-credentials';
import { Credentials } from '@data/model/credentials.model';
import { Authentication } from '@data/model/authentication.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { GlobalService } from './global.service';
import { User } from '@data/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private router: Router, private globalService: GlobalService, private http: HttpClient) {
    const credentialsKey = environment.credentialsKey;
    const currentUser = localStorage.getItem(credentialsKey);
    if (currentUser) {
      this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(currentUser));
    }
    this.currentUser = this.currentUserSubject.asObservable();
  }

  login(authentication: Authentication): Observable<any> {
    const url = `${environment.apiUrl}/auth/signin`;
    return this.http.post<ApiCredentials>(url, Authentication.adaptForAPI(authentication)).pipe(
      map((response) => {
        this.globalService.setCredentials(Credentials.adapt(response));
        return Credentials.adapt(response);
      })
    );
  }

  logout(): void {
    // console.log('logout');
    this.globalService.setCredentials();
  }

  unauthorizedAccess(): void {
    // console.log('unauthorizedAccess');
    this.logout();
    this.router.navigate(['/auth/login']);
  }
}
