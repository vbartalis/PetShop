import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiTag } from '@data/api/api-tag';
import { Tag } from '@data/model/tag.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TagDataService {
  constructor(private http: HttpClient) {}

  getTags(): Observable<any> {
    const url = `${environment.apiUrl}/tag`;
    return this.http.get<ApiTag[]>(url).pipe(map((response) => response.map((t) => Tag.adapt(t))));
  }

  getTagById(id: number): Observable<any> {
    const url = `${environment.apiUrl}/tag/`;
    return this.http.get<ApiTag>(url + id).pipe(map((response) => Tag.adapt(response)));
  }

  createTag(tag: Tag): Observable<any> {
    const url = `${environment.apiUrl}/tag`;
    return this.http.post<ApiTag>(url, Tag.adaptForApi(tag)).pipe(map((response) => Tag.adapt(response)));
  }

  updateTag(tag: Tag): Observable<any> {
    const url = `${environment.apiUrl}/tag/`;
    return this.http.put<ApiTag>(url + tag.id, Tag.adaptForApi(tag)).pipe(map((response) => Tag.adapt(response)));
  }
}
