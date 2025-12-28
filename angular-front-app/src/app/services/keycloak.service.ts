import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  private _keycloak: Keycloak | undefined;

  get keycloak() {
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8080', // URL de votre Keycloak
        realm: 'sd-conference',       // Nom du Realm
        clientId: 'conference-front-client' // Client ID
      });
    }
    return this._keycloak;
  }

  async init() {
    const authenticated = await this.keycloak.init({
      onLoad: 'login-required', // Force le login dès l'ouverture de l'app
    });

    if (authenticated) {
      console.log('User authenticated');
    }
  }

  get token() {
    return this.keycloak.token;
  }

  login() {
    return this.keycloak.login();
  }

  logout() {
    // Redirige vers la base de l'app après logout
    return this.keycloak.logout({ redirectUri: 'http://localhost:4200' });
  }

  get userProfile() {
    return this.keycloak.loadUserProfile();
  }
  get roles(): string[] {
    return this.keycloak.realmAccess?.roles || [];
  }

  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }
}
