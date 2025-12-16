import { Routes } from '@angular/router';
import { ConferencesComponent } from './conferences/conferences.component';

export const routes: Routes = [
  // Route par défaut qui redirige vers /conferences
  { path: '', redirectTo: 'conferences', pathMatch: 'full' },
  // La route qui affiche notre composant
  { path: 'conferences', component: ConferencesComponent }
];
