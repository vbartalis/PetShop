export class PostSearchCriteria {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    //todo boolean
    public isPublic?: string,
    public userId?: number,
    public tagIds?: number[]
  ) {}
}

export class UserSearchCriteria {
  constructor(
    public id?: number,
    public username?: string,
    //todo boolean
    public isLocked?: string
  ) {}
}
