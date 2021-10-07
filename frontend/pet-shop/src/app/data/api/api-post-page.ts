import { ApiPost } from './api-post';

export interface ApiPostPage {
  totalPages: number;
  totalElements: number;
  size: number;
  content: ApiPost[];
  number: number;
  sort: ApiSort;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  pageable: ApiPageable;
  empty: boolean;
}

export interface ApiSort {
  sorted: boolean;
  unsorted: boolean;
  empty: boolean;
}

export interface ApiPageable {
  offset: boolean;
  sort: ApiSort;
  pageSize: number;
  pageNumber: number;
  unpaged: boolean;
  paged: boolean;
}
