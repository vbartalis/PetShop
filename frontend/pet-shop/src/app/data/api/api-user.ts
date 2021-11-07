import { ApiProfile } from './api-profile';
import { ApiRole } from './api-role';

export class ApiUser {
  constructor(
    public id: number,
    public username: string,
    public isLocked: boolean,
    public expiration: Date,
    public roles: ApiRole[],
    public password?: string,
    public profile?: ApiProfile
  ) {}
}
