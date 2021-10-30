import { ApiPost } from '@data/api/api-post';
import { Tag } from './tag.model';

export class Post {
  constructor(
    public id: number,
    public title: string,
    public description: string,
    public creationDate: Date,
    public updateDate: Date,
    public isPublic: boolean,
    public tags: Tag[],
    public postImageId?: number,
    public userId?: number,
    public profileId?: number
  ) {}

  static adapt(value: ApiPost): Post {
    return new Post(
      value.id,
      value.title,
      value.description,
      value.creationDate,
      value.updateDate,
      value.isPublic,
      value.tags.map((i) => Tag.adapt(i)),
      value.postImage?.id,
      value.user?.id,
      value.user?.profile?.id
    );
  }

  static adaptForApi(value: Post): ApiPost {
    return new ApiPost(
      value.id,
      value.title,
      value.description,
      value.creationDate,
      value.updateDate,
      value.isPublic,
      value.tags.map((i) => Tag.adaptForApi(i))
    );
  }
}
