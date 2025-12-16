import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  // METTRE A JOUR CES DEUX LIGNES :
  templateUrl: './app.component.html', // avant: './app.html'
  styleUrl: './app.component.css'      // avant: './app.css'
})
export class AppComponent {
  title = 'conference-app';
}
