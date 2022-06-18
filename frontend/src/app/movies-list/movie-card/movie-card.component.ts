import { Component, Input } from '@angular/core';
import { MovieModel } from 'src/app/model/movie.model';

@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.scss']
})
export class MovieCardComponent {

  @Input() movie!: MovieModel;

  constructor() { }

}
