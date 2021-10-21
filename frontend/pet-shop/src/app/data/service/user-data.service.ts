import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiUser } from '@data/api/api-user';
import { ApiUserPage } from '@data/api/api-user-page';
import { UserPageCriteria } from '@data/api/page-criteria';
import { UserSearchCriteria } from '@data/api/search-criteria';
import { UserPage } from '@data/model/user-page';
import { User } from '@data/model/user.model';
import { forIn } from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  constructor(private http: HttpClient) {}

  // todo environment api url, should also search by tags
  getAll(userPageCriteria: UserPageCriteria, userSearchCriteria: UserSearchCriteria): Observable<UserPage> {
    const url = `${environment.apiUrl}/user`;
    let params = new HttpParams();

    forIn(userPageCriteria, (value, key) => {
      if (value != null) {
        if (key == 'pageNumber') {
          const pageNumber = (value as number) - 1;
          params = params.append(key, JSON.stringify(pageNumber));
        } else {
          params = params.append(key, JSON.stringify(value));
        }
      }
    });
    forIn(userSearchCriteria, (value, key) => {
      if (value != null) {
        params = params.append(key, JSON.stringify(value));
      }
    });

    return this.http.get<ApiUserPage>(url, { params }).pipe(map((response) => UserPage.adapt(response)));
  }

  getUserById(id: number): Observable<User> {
    const url = `${environment.apiUrl}/user/`;
    return this.http.get<ApiUser>(url + id).pipe(
      map((response) => {
        return User.adapt(response);
      })
    );
  }

  getCurrentUser(): Observable<User> {
    const url = `${environment.apiUrl}/service/currentUser`;
    return this.http.get<ApiUser>(url).pipe(
      map((response) => {
        return User.adapt(response);
      })
    );
  }

  createUserByid(user: User): Observable<any> {
    const url = `${environment.apiUrl}/user`;
    return this.http.post<ApiUser>(url, User.adaptForApi(user)).pipe(map((response) => User.adapt(response)));
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
