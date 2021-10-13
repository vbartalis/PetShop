import { ApiPost } from '@data/api/api-post';
import { ApiTag } from '@data/api/api-tag';

export class Tag {
  constructor(public id: number, public name: string, public description: string) {}

  static adapt(value: ApiTag): Tag {
    return new Tag(value.id, value.name, value.description);
  }

  static adaptForApi(value: Tag): ApiTag {
    return new ApiTag(value.id, value.name, value.description);
  }
}
