import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Certificate } from '../Model/certificate.js';


@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private baseUrl = 'http://localhost:8083/api/certificates'; // ⚠️ adapte le port selon ton MS

  constructor(private http: HttpClient) {}

  getAll(): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.baseUrl);
  }

  getByUser(userId: number): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(`${this.baseUrl}/user/${userId}`);
  }

  getById(id: number): Observable<Certificate> {
    return this.http.get<Certificate>(`${this.baseUrl}/${id}`);
  }

  create(cert: Certificate): Observable<Certificate> {
    return this.http.post<Certificate>(this.baseUrl, cert);
  }

  update(id: number, cert: Certificate): Observable<Certificate> {
    return this.http.put<Certificate>(`${this.baseUrl}/${id}`, cert);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getPdf(fileName: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/pdf/${fileName}`, { responseType: 'blob' });
  }
}
