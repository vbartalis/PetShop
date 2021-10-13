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
    const url = `${environment.apiUrl}/tag/`;
    return this.http.get<ApiTag[]>(url).pipe(map((response) => response.map((t) => Tag.adapt(t))));
  }
}
