import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiProfileImage } from '@data/api/api-profile-image';
import { ProfileImage } from '@data/model/profile-image.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProfileImageDataService {
  constructor(private http: HttpClient) {}

  //todo
  getProfileImageById(id: number): Observable<any> {
    const url = `${environment.apiUrl}/profileimage/`;
    return this.http.get(url + id, { responseType: 'text' });
  }

  updateProfileImage(id: number, image: File): Observable<any> {
    const url = `${environment.apiUrl}/profileimage/`;
    const formData = new FormData();
    formData.append('image', image);
    return this.http.put<ApiProfileImage>(url + id, formData).pipe(map((response) => ProfileImage.adapt(response)));
  }

  deleteProfileImage(id: number): Observable<any> {
    const url = `${environment.apiUrl}/profileimage/`;
    return this.http.delete(url + id);
  }
}
