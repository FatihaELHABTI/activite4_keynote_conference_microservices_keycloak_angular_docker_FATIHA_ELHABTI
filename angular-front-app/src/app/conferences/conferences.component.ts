import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <--- 1. IMPORTER ChangeDetectorRef
import { CommonModule } from '@angular/common'; // <--- 2. TOUJOURS VERIFIER QUE C'EST LA
import { ConferenceService } from '../services/conference.service';
import { Conference } from '../models/models';

@Component({
  selector: 'app-conferences',
  standalone: true,
  imports: [CommonModule], // <--- 3. VERIFIER QUE C'EST BIEN DANS CE TABLEAU
  templateUrl: './conferences.component.html',
  styleUrl: './conferences.component.css'
})
export class ConferencesComponent implements OnInit {
  conferences: Array<Conference> = [];
  errorMessage: string = "";

  // 4. INJECTER ChangeDetectorRef (cdr)
  constructor(
    private confService: ConferenceService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.confService.getConferences().subscribe({
      next: (data) => {
        this.conferences = data;
        // 5. FORCER LA MISE A JOUR MANUELLE
        this.cdr.detectChanges();
        console.log("Mise à jour de l'écran forcée avec :", data);
      },
      error: (err) => {
        this.errorMessage = "Erreur chargement.";
        console.error(err);
      }
    });
  }
}
