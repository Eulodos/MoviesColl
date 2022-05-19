import { Component, OnInit } from '@angular/core';
import { MoviesService } from '../movies.service';

@Component({
  selector: 'app-movies-list',
  templateUrl: './movies-list.component.html',
  styleUrls: ['./movies-list.component.scss']
})
export class MoviesListComponent implements OnInit {

  someFunkyS: any;

  constructor(private moviesService: MoviesService) { }

  ngOnInit(): void {
    this.moviesService.getPopular()
      .subscribe(data => {
        console.log("Data coming back is: ", data);
        this.someFunkyS = data;
      })
  }


}
