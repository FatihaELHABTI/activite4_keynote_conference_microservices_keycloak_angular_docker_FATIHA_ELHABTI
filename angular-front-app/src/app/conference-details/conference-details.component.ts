import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ConferenceService } from '../services/conference.service';
import { Conference } from '../models/models';

@Component({
  selector: 'app-conference-details',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // RouterLink pour le bouton retour
  templateUrl: './conference-details.component.html',
  styleUrl: './conference-details.component.css' // (ou .css selon votre fichier)
})
export class ConferenceDetailsComponent implements OnInit {
  conference!: Conference;
  // Objet pour le nouveau commentaire
  newReview: any = { stars: 5, texte: '' };

  constructor(
    private route: ActivatedRoute,
    private confService: ConferenceService
  ) {}

  ngOnInit() {
    // Récupérer l'ID depuis l'URL
    const id = this.route.snapshot.params['id'];
    this.refresh(id);
  }

  refresh(id: number) {
    this.confService.getConferenceById(id).subscribe(data => {
      this.conference = data;
    });
  }

  submitReview() {
    if (!this.newReview.texte) {
      alert("Veuillez écrire un commentaire.");
      return;
    }

    this.confService.addReview(this.newReview, this.conference.id).subscribe({
      next: () => {
        this.newReview = { stars: 5, texte: '' }; // Reset
        this.refresh(this.conference.id); // Recharger pour voir l'avis ajouté
      },
      error: (err) => alert("Erreur lors de l'ajout de l'avis")
    });
  }
}
