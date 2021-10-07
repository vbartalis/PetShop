import { ApiTag } from '@data/api/api-tag';

export class Tag {
  constructor(public id: number, public name: string, public description: string) {}

  static adapt(value: ApiTag): Tag {
    return new Tag(value.id, value.name, value.description);
  }
}
