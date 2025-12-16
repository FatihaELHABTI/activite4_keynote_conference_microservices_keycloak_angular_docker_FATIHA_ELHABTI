import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from '../services/keycloak.service';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const kcService = inject(KeycloakService);
  const token = kcService.token;

  // Si on a un token, on clone la requête et on ajoute le Header Authorization
  if (token) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(authReq);
  }

  // Sinon on laisse passer tel quel (ça échouera probablement en 401)
  return next(req);
};
