import { ApiPostImage } from '@data/api/api-post-image';

export class PostImage {
  constructor(public id: number, public img?: Blob) {}

  static adapt(value: ApiPostImage): PostImage {
    return new PostImage(value.id);
  }
}
