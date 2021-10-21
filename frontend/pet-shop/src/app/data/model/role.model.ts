import { ApiRole } from '@data/api/api-role';

export class Role {
  constructor(public id: number, public name: string) {}

  static adapt(value: ApiRole): Role {
    return new Role(value.id, value.name);
  }

  static adaptForApi(value: Role): ApiRole {
    return new ApiRole(value.id, value.name);
  }
}
