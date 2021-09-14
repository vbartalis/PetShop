import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiCredentials } from '@data/api/api-credentials';
import { Credentials } from '@data/model/credentials.model';
import { LoginContext } from '@data/model/login-context';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { GlobalService } from './global.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private router: Router, private globalService: GlobalService, private http: HttpClient) {}

  login(loginContext: LoginContext): Observable<any> {
    const url = `${environment.apiUrl}/auth/signin`;
    return this.http.post<ApiCredentials>(url, LoginContext.adaptForAPI(loginContext)).pipe(
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
