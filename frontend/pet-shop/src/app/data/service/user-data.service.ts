import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiUser } from '@data/api/api-user';
import { User } from '@data/model/user.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<User> {
    const url = `${environment.apiUrl}/service/currentUser`;
    return this.http.get<ApiUser>(url).pipe(
      map((response) => {
        return User.adapt(response);
      })
    );
  }

  // todo
  updateUserPassword(user: User): Observable<User> {
    const url = `${environment.apiUrl}/user/`;
    const body = { password: user.password };
    return this.http.patch<ApiUser>(url + user.id, body).pipe(
      map((response) => {
        return User.adapt(response);
      })
    );
  }
}
