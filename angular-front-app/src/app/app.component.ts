import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <--- Import
import { RouterOutlet, RouterLink } from '@angular/router';
import { KeycloakService } from './services/keycloak.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  username: string = '';

  // Injection
  constructor(
    private kcService: KeycloakService,
    private cdr: ChangeDetectorRef
  ) {}

  async ngOnInit() {
    if (this.kcService.keycloak.authenticated) {
      const profile = await this.kcService.userProfile;

      // Affectation des données
      this.username = (profile.firstName || '') + ' ' + (profile.lastName || '');

      // Force Update
      this.cdr.detectChanges();
    }
  }

  logout() {
    this.kcService.logout();
  }
}
