import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
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
}
