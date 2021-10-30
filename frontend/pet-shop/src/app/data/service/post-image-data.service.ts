import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiPostImage } from '@data/api/api-post-image';
import { PostImage } from '@data/model/post-image.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PostImageDataService {
  constructor(private http: HttpClient) {}

  getPostImageById(id: number): Observable<any> {
    const url = `${environment.apiUrl}/postimage/`;
    return this.http.get(url + id, { responseType: 'text' });
  }

  updatePostImage(id: number, image: File): Observable<any> {
    const url = `${environment.apiUrl}/postimage/`;
    const formData = new FormData();
    formData.append('image', image);
    return this.http.put<ApiPostImage>(url + id, formData).pipe(map((response) => PostImage.adapt(response)));
  }

  deletePostImage(id: number): Observable<any> {
    const url = `${environment.apiUrl}/postimage/`;
    return this.http.delete(url + id);
  }
}
