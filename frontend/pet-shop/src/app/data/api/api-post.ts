import { ApiPostImage } from './api-post-image';
import { ApiTag } from './api-tag';
import { ApiUser } from './api-user';

export interface ApiPost {
  id: number;
  title: string;
  description: string;
  creationDate: Date;
  updateDate: Date;
  isPublic: boolean;
  user: ApiUser;
  postImage: ApiPostImage;
  tags: ApiTag[];
}