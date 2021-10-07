export class PostPageCriteria {
  constructor(
    public pageNumber: number,
    public pageSize: number,
    public sortDirection?: string,
    public sortBy?: string
  ) {}
}
