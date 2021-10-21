import { ApiUserPage } from '@data/api/api-user-page';
import { Pageable, Sort } from './pageable';
import { User } from './user.model';

export class UserPage {
  constructor(
    public totalPages: number,
    public totalElements: number,
    public size: number,
    public content: User[],
    public number: number,
    public sort: Sort,
    public first: boolean,
    public last: boolean,
    public numberOfElements: number,
    public pageable: Pageable,
    public empty: boolean
  ) {}

  static adapt(value: ApiUserPage): UserPage {
    return new UserPage(
      value.totalPages,
      value.totalElements,
      value.size,
      value.content.map((i) => User.adapt(i)),
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
