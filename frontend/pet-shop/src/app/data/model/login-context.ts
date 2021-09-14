import { ApiAuthentication } from '@data/api/api-authentication';

export class LoginContext {
  constructor(public username: string, public password: string) {}

  static adaptForAPI(loginContext: LoginContext): ApiAuthentication {
    return {
      username: loginContext.username,
      password: loginContext.password,
    };
  }
}
