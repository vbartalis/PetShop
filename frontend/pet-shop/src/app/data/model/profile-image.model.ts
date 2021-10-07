import { ApiProfileImage } from '@data/api/api-profile-image';

export class ProfileImage {
  constructor(public id: number, public img?: Blob) {}

  static adapt(value: ApiProfileImage): ProfileImage {
    return new ProfileImage(value.id);
  }
}
