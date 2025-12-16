import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Conference } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class ConferenceService {
  // On pointe vers la GATEWAY (Port 8888)
  private HOST = "http://localhost:8888/conference-service/api/conferences";

  constructor(private http: HttpClient) { }

  public getConferences(): Observable<Array<Conference>> {
    return this.http.get<Array<Conference>>(this.HOST);
  }
}
