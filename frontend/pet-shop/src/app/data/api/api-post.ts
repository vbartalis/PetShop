import { ApiPostImage } from './api-post-image';
import { ApiTag } from './api-tag';
import { ApiUser } from './api-user';

export class ApiPost {
  constructor(
    public id: number,
    public title: string,
    public description: string,
    public creationDate: Date,
    public updateDate: Date,
    public isPublic: boolean,
    public tags: ApiTag[],
    public postImage?: ApiPostImage,
    public user?: ApiUser
  ) {}
}
