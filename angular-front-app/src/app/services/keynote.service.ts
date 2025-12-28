import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Keynote } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class KeynoteService {
  // On passe par la Gateway pour atteindre le keynote-service
  private HOST = "http://localhost:8888/keynote-service/api/keynotes";

  constructor(private http: HttpClient) { }

  public getAllKeynotes(): Observable<Array<Keynote>> {
    return this.http.get<Array<Keynote>>(this.HOST);
  }
}
