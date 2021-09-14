import { ApiCredentials } from '@data/api/api-credentials';

export class Credentials {
  constructor(public username: string, public jwt: string, public roles: string[]) {}

  static adapt(apiCredentials: ApiCredentials): Credentials {
    const credentials = new Credentials(apiCredentials.username, apiCredentials.jwt, apiCredentials.roles);

    return credentials;
  }
}
