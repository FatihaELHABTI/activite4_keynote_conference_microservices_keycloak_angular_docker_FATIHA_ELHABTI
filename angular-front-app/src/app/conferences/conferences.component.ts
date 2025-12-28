import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConferenceService } from '../services/conference.service';
import { KeycloakService } from '../services/keycloak.service';
import { KeynoteService } from '../services/keynote.service';
import { Conference, Keynote } from '../models/models';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-conferences',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterLink],
  templateUrl: './conferences.component.html',
  styleUrl: './conferences.component.css'
})
export class ConferencesComponent implements OnInit {
  conferences: Array<Conference> = [];
  keynotes: Array<Keynote> = [];
  isAdmin = false;
  showForm = false;
  isEditMode = false; // Pour savoir si on modifie

  // Objet formulaire
  newConf: any = { type: 'Academique', keynoteId: null };

  constructor(
    private confService: ConferenceService,
    private knService: KeynoteService,
    private kcService: KeycloakService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.kcService.hasRole('ADMIN');
    this.refresh();

    if (this.isAdmin) {
      this.knService.getAllKeynotes().subscribe(data => this.keynotes = data);
    }
  }

  refresh() {
    this.confService.getConferences().subscribe(data => {
      this.conferences = data;
      this.cdr.detectChanges();
    });
  }

  // MODIFIÉ : Accepte une conférence optionnelle pour l'édition
  toggleForm(confToEdit?: Conference) {
    this.showForm = true; // On affiche le formulaire

    if (confToEdit) {
      // MODE ÉDITION
      this.isEditMode = true;
      // On clone l'objet pour ne pas modifier l'affichage direct
      this.newConf = { ...confToEdit };
      // Important : Mapper l'objet keynote vers l'ID pour le selecteur
      if (confToEdit.keynote) {
        this.newConf.keynoteId = confToEdit.keynote.id;
      }
    } else {
      // MODE AJOUT (Reset)
      // Si le formulaire est déjà ouvert en mode ajout, on le ferme
      if (!this.isEditMode && this.showForm) {
        this.showForm = !this.showForm; // Toggle classique
      }
      this.isEditMode = false;
      this.newConf = { type: 'Academique', keynoteId: null, score: 0, nombreInscrits: 0 };
    }
  }

  closeForm() {
    this.showForm = false;
    this.isEditMode = false;
  }

  onSubmit() {
    if (!this.newConf.keynoteId) {
      alert("Veuillez sélectionner un Speaker !");
      return;
    }

    if (this.isEditMode) {
      // MISE A JOUR
      this.confService.updateConference(this.newConf.id, this.newConf).subscribe({
        next: () => {
          this.refresh();
          this.closeForm();
        },
        error: (err) => alert("Erreur modification")
      });
    } else {
      // AJOUT
      this.confService.addConference(this.newConf).subscribe({
        next: () => {
          this.refresh();
          this.closeForm();
        },
        error: (err) => alert("Erreur ajout")
      });
    }
  }

  deleteConf(id: number) {
    if(confirm("Supprimer cette conférence ?")) {
      this.confService.deleteConference(id).subscribe(() => this.refresh());
    }
  }
}
