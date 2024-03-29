import { ApiProfile } from '@data/api/api-profile';
import { ProfileImage } from './profile-image.model';
import { User } from './user.model';

export class Profile {
  constructor(
    public id: number,
    public name: string,
    public email: string,
    public address: string,
    public description: string,
    public joinDate: Date,
    public profileImageId?: number // public user?: User
  ) {}

  static adapt(value: ApiProfile): Profile {
    return new Profile(
      value.id,
      value.name,
      value.email,
      value.address,
      value.description,
      value.joinDate,
      value.profileImage?.id
    );
  }

  static adaptForApi(value: Profile): ApiProfile {
    return new ApiProfile(value.id, value.name, value.email, value.address, value.description, value.joinDate);
  }
}
