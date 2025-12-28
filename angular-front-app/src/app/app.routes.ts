import { Routes } from '@angular/router';
import { ConferencesComponent } from './conferences/conferences.component';
import { ConferenceDetailsComponent } from './conference-details/conference-details.component'; // <--- Import

export const routes: Routes = [
  { path: '', redirectTo: 'conferences', pathMatch: 'full' },
  { path: 'conferences', component: ConferencesComponent },
  { path: 'conferences/:id', component: ConferenceDetailsComponent } // <--- Nouvelle route
];
