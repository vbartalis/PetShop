import { ApiPost } from '@data/api/api-post';
import { PostImage } from './post-image.model';
import { Tag } from './tag.model';
import { User } from './user.model';

export class Post {
  constructor(
    public id: number,
    public title: string,
    public description: string,
    public creationDate: Date,
    public updateDate: Date,
    public isPublic: boolean,
    public user: User,
    public postImage: PostImage,
    public tags: Tag[]
  ) {}

  static adapt(value: ApiPost): Post {
    return new Post(
      value.id,
      value.title,
      value.description,
      value.creationDate,
      value.updateDate,
      value.isPublic,
      User.adapt(value.user),
      PostImage.adapt(value.postImage),
      value.tags.map((i) => Tag.adapt(i))
    );
  }
}
