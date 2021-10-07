import { ApiPageable, ApiPostPage, ApiSort } from '@data/api/api-post-page';
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

class Sort {
  constructor(public sorted: boolean, public unsorted: boolean, public empty: boolean) {}

  static adapt(value: ApiSort): Sort {
    return new Sort(value.sorted, value.unsorted, value.empty);
  }
}

class Pageable {
  constructor(
    public offset: boolean,
    public sort: Sort,
    public pageSize: number,
    public pageNumber: number,
    public unpaged: boolean,
    public paged: boolean
  ) {}

  static adapt(value: ApiPageable): Pageable {
    return new Pageable(value.offset, value.sort, value.pageSize, value.pageNumber, value.unpaged, value.paged);
  }
}
