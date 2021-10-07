import { ApiProfile } from './api-profile';
import { ApiRole } from './api-role';

export interface ApiUser {
  id: number;
  username: string;
  password: string;
  isLocked: boolean;
  expiration: Date;
  profile: ApiProfile;
  roles: ApiRole[];
}
