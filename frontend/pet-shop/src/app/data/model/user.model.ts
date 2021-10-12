import { ApiUser } from '@data/api/api-user';
import { Profile } from './profile.model';
import { Role } from './role.model';

export class User {
  constructor(
    public id: number,
    public username: string,
    public isLocked: boolean,
    public expiration: Date,
    public roles: Role[],
    public profileId?: number,
    public password?: string
  ) {}

  static adapt(value: ApiUser): User {
    return new User(
      value.id,
      value.username,
      value.isLocked,
      value.expiration,
      value.roles.map((i) => Role.adapt(i)),
      value.profile.id
    );
  }
}
