import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Conference } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {
  // URL de base passant par la Gateway (Port 8888) vers le micro-service conference
  private HOST = "http://localhost:8888/conference-service/api/conferences";

  // URL pour les reviews (pointant vers le ReviewController qu'on a créé)
  private REVIEW_HOST = "http://localhost:8888/conference-service/api/reviews";

  constructor(private http: HttpClient) { }

  // ==========================================
  // GESTION DES CONFÉRENCES
  // ==========================================

  // 1. Récupérer toutes les conférences
  public getConferences(): Observable<Array<Conference>> {
    return this.http.get<Array<Conference>>(this.HOST);
  }

  // 2. Récupérer une seule conférence par son ID (Pour la page Détails)
  public getConferenceById(id: number): Observable<Conference> {
    return this.http.get<Conference>(`${this.HOST}/${id}`);
  }

  // 3. Ajouter une nouvelle conférence
  public addConference(conf: Conference): Observable<Conference> {
    return this.http.post<Conference>(this.HOST, conf);
  }

  // 4. Mettre à jour une conférence existante
  public updateConference(id: number, conf: Conference): Observable<Conference> {
    return this.http.put<Conference>(`${this.HOST}/${id}`, conf);
  }

  // 5. Supprimer une conférence
  public deleteConference(id: number): Observable<void> {
    return this.http.delete<void>(`${this.HOST}/${id}`);
  }

  // ==========================================
  // GESTION DES REVIEWS (AVIS)
  // ==========================================

  // 6. Ajouter un review à une conférence spécifique
  // L'URL sera : .../api/reviews?conferenceId=1
  public addReview(review: any, confId: number): Observable<any> {
    return this.http.post<any>(`${this.REVIEW_HOST}?conferenceId=${confId}`, review);
  }
}
