import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MoviesService {

  private url: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getPopular(): Observable<any> {
    console.log("Before making call");
    return this.http.get<any>(this.url);
  }
}
