import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiProfile } from '@data/api/api-profile';
import { Profile } from '@data/model/profile.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProfileDataService {
  constructor(private http: HttpClient) {}

  updateProfile(id: number, profile: Profile): Observable<Profile> {
    const url = `${environment.apiUrl}/profile/`;
    return this.http.get<ApiProfile>(url + id).pipe(
      map((response) => {
        return Profile.adapt(response);
      })
    );
  }

  getProfileById(id: number): Observable<Profile> {
    const url = `${environment.apiUrl}/profile/`;

    const body = Profile.adapt;

    return this.http.patch<ApiProfile>(url + id).pipe(
      map((response) => {
        return Profile.adapt(response);
      })
    );
  }
}
