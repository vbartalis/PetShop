import { ApiSort, ApiPageable } from './api-pageable';
import { ApiUser } from './api-user';

export interface ApiUserPage {
  totalPages: number;
  totalElements: number;
  size: number;
  content: ApiUser[];
  number: number;
  sort: ApiSort;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  pageable: ApiPageable;
  empty: boolean;
}
