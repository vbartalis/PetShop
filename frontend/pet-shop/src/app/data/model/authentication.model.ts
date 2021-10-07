import { ApiAuthentication } from '@data/api/api-authentication';

export class Authentication {
  constructor(public username: string, public password: string) {}

  static adaptForAPI(authentication: Authentication): ApiAuthentication {
    return {
      username: authentication.username,
      password: authentication.password,
    };
  }
}
