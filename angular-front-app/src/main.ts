import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
// CORRIGER L'IMPORT ICI :
import { AppComponent } from './app/app.component'; // avant: './app/app'

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
