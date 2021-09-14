import { Injectable } from '@angular/core';
import { Credentials } from '@data/model/credentials.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class GlobalService {
  #credentials: Credentials | null = null;

  constructor() {
    this.readCredentials();
  }

  setCredentials(credentials?: Credentials): void {
    if (credentials) {
      const storage = localStorage;
      storage.setItem(environment.credentialsKey, JSON.stringify(credentials));
    } else {
      localStorage.removeItem(environment.credentialsKey);
    }
  }

  readCredentials(): void {
    const savedCredentials = localStorage.getItem(environment.credentialsKey);
    this.#credentials = savedCredentials ? JSON.parse(savedCredentials) : null;
  }

  get credentials(): Credentials | null {
    return this.#credentials;
  }

  isLoggedIn(): boolean {
    this.readCredentials();
    return this.#credentials !== null;
  }

  isLoggedInUser(): boolean {
    this.readCredentials();
    if (this.#credentials == null) {
      return false;
    }
    return this.#credentials.roles.includes('ROLE_USER');
  }

  isLoggedInAdmin(): boolean {
    this.readCredentials();
    if (this.#credentials == null) {
      return false;
    }
    return this.#credentials.roles.includes('ROLE_ADMIN');
  }
}
