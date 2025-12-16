import { ApplicationConfig, APP_INITIALIZER } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import { KeycloakService } from './services/keycloak.service';
import { tokenInterceptor } from './interceptors/token.interceptor'; // On va le créer juste après

// Fonction Factory pour lancer l'init
export function kcFactory(kcService: KeycloakService) {
  return () => kcService.init();
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // 1. On configure HTTP avec l'intercepteur (qu'on va créer étape 4)
    provideHttpClient(withInterceptors([tokenInterceptor])),

    // 2. On initialise Keycloak au démarrage
    {
      provide: APP_INITIALIZER,
      useFactory: kcFactory,
      deps: [KeycloakService],
      multi: true
    }
  ]
};
