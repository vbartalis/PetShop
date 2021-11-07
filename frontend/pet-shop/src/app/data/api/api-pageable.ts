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
