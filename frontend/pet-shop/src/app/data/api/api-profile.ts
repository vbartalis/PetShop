import { ApiProfileImage } from './api-profile-image';
import { ApiUser } from './api-user';

export class ApiProfile {
  constructor(
    public id: number,
    public name: string,
    public email: string,
    public address: string,
    public description: string,
    public joinDate: Date,
    public profileImage?: ApiProfileImage,
    public user?: ApiUser
  ) {}
}
