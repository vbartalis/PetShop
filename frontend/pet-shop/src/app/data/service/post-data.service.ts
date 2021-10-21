import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiPost } from '@data/api/api-post';
import { ApiPostPage } from '@data/api/api-post-page';
import { PostPageCriteria } from '@data/api/page-criteria';
import { PostSearchCriteria } from '@data/api/search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
import { forIn } from 'lodash';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PostDataService {
  constructor(private http: HttpClient) {}

  // todo environment api url, should also search by tags
  getAll(postPageCriteria: PostPageCriteria, postSearchCriteria: PostSearchCriteria): Observable<PostPage> {
    const url = `${environment.apiUrl}/post`;
    let params = new HttpParams();

    forIn(postPageCriteria, (value, key) => {
      if (value != null) {
        if (key == 'pageNumber') {
          const pageNumber = (value as number) - 1;
          params = params.append(key, JSON.stringify(pageNumber));
        } else params = params.append(key, JSON.stringify(value));
      }
    });
    forIn(postSearchCriteria, (value, key) => {
      if (value != null) {
        params = params.append(key, JSON.stringify(value));
      }
    });

    return this.http.get<ApiPostPage>(url, { params }).pipe(map((response) => PostPage.adapt(response)));
  }

  getPostById(id: number): Observable<Post> {
    const url = `${environment.apiUrl}/post/`;
    return this.http.get<ApiPost>(url + id).pipe(
      map((response) => {
        return Post.adapt(response);
      })
    );
  }

  deletePost(id: number): Observable<any> {
    const url = `${environment.apiUrl}/post/`;
    return this.http.delete(url + id);
  }

  createPost(post: Post): Observable<Post> {
    const url = `${environment.apiUrl}/post`;
    const body = Post.adaptForApi(post);
    return this.http.post<ApiPost>(url, body).pipe(
      map((response) => {
        return Post.adapt(response);
      })
    );
  }

  updatePost(post: Post): Observable<Post> {
    const url = `${environment.apiUrl}/post/`;
    const body = Post.adaptForApi(post);
    return this.http.put<ApiPost>(url + post.id, body).pipe(
      map((response) => {
        return Post.adapt(response);
      })
    );
  }
}
