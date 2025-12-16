import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <--- 1. IMPORTER CECI
import { ConferenceService } from '../services/conference.service';
import { Conference } from '../models/models';

@Component({
  selector: 'app-conferences',
  standalone: true,
  imports: [CommonModule], // <--- 2. AJOUTER CECI DANS LE TABLEAU
  templateUrl: './conferences.component.html',
  styleUrl: './conferences.component.css'
})
export class ConferencesComponent implements OnInit {
  // ... le reste du code est bon (ne changez rien en bas)
  conferences: Array<Conference> = [];
  errorMessage: string = "";

  constructor(private confService: ConferenceService) { }

  ngOnInit(): void {
    this.confService.getConferences().subscribe({
      next: (data) => {
        this.conferences = data;
        console.log(data);
      },
      error: (err) => {
        this.errorMessage = "Erreur chargement.";
        console.error(err);
      }
    });
  }
}
