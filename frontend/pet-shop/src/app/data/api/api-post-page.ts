import { ApiSort, ApiPageable } from './api-pageable';
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
