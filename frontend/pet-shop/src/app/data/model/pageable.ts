import { ApiPageable, ApiSort } from '@data/api/api-pageable';

export class Pageable {
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

export class Sort {
  constructor(public sorted: boolean, public unsorted: boolean, public empty: boolean) {}

  static adapt(value: ApiSort): Sort {
    return new Sort(value.sorted, value.unsorted, value.empty);
  }
}
