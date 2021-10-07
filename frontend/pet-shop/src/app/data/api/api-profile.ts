import { ApiProfileImage } from './api-profile-image';
import { ApiUser } from './api-user';

export interface ApiProfile {
  id: number;
  name: string;
  email: string;
  address: string;
  description: string;
  joinDate: Date;
  profileImage: ApiProfileImage;
  user: ApiUser;
}
