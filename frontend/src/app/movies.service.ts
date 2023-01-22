import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { MovieModelPageable } from './model/movie-pageable.model';

@Injectable({
  providedIn: 'root',
})
export class MoviesService {
  url: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getPopular(): Observable<MovieModelPageable> {
    console.log('Fetching list of movies');
    return this.http
      .get<MovieModelPageable>(`${this.url}/movies/popular`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // Return an observable with a user-facing error message.
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
