import { Component, OnInit } from '@angular/core';
import { catchError, map, of } from 'rxjs';
import { MovieModelPageable } from '../model/movie-pageable.model';
import { MovieModel } from '../model/movie.model';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.scss']
})
export class MoviesListComponent implements OnInit {

  movies!: MovieModel[];

  constructor(private moviesService: MoviesService) { }

  ngOnInit(): void {
    this.moviesService.getPopular()
      .pipe(
        map((data: MovieModelPageable) => {
          if (!data.results) {
            throw new Error("No value present")
          }
          return data.results;
        }),
        catchError((err) => {
          console.error(err);
          return of([]);
        })
      )
      .subscribe((data: MovieModel[]) => {
        console.log("Data coming back is: ", data);
        this.movies = data;
      });
  }
}
