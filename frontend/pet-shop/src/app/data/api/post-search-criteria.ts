export class PostSearchCriteria {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public isPublic?: string,
    public userId?: number
  ) {}
}
