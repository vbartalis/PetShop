import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiPost } from '@data/api/api-post';
import { ApiPostPage } from '@data/api/api-post-page';
import { PostPageCriteria } from '@data/api/post-page-criteria';
import { PostSearchCriteria } from '@data/api/post-search-criteria';
import { PostPage } from '@data/model/post-page';
import { Post } from '@data/model/post.model';
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
    params = params.append('postPageCriteria', JSON.stringify(postPageCriteria));
    params = params.append('postSearchCriteria', JSON.stringify(postSearchCriteria));

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
}
