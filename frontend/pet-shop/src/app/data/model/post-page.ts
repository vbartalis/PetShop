import { ApiPostPage } from '@data/api/api-post-page';
import { Sort, Pageable } from './pageable';
import { Post } from './post.model';

export class PostPage {
  constructor(
    public totalPages: number,
    public totalElements: number,
    public size: number,
    public content: Post[],
    public number: number,
    public sort: Sort,
    public first: boolean,
    public last: boolean,
    public numberOfElements: number,
    public pageable: Pageable,
    public empty: boolean
  ) {}

  static adapt(value: ApiPostPage): PostPage {
    return new PostPage(
      value.totalPages,
      value.totalElements,
      value.size,
      value.content.map((i) => Post.adapt(i)),
      value.number,
      Sort.adapt(value.sort),
      value.first,
      value.last,
      value.numberOfElements,
      Pageable.adapt(value.pageable),
      value.empty
    );
  }
}
