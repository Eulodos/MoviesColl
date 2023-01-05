import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatLegacyCard as MatCard, MatLegacyCardActions as MatCardActions, MatLegacyCardContent as MatCardContent, MatLegacyCardTitle as MatCardTitle } from '@angular/material/legacy-card';
import { By } from '@angular/platform-browser';
import { of, throwError } from 'rxjs';
import { MoviesService } from '../movies.service';
import { MovieCardComponent } from './movie-card/movie-card.component';
import { MoviesListComponent } from './movies-list.component';

const mockResponse = {
  "totalResults": 20000,
  "totalPages": 1000,
  "page": 1,
  "results": [
    {
      "backdropPath": "/gG9fTyDL03fiKnOpf2tr01sncnt.jpg",
      "genreIds": [
        28,
        878,
        14
      ],
      "originalLanguage": "en",
      "originalTitle": "Morbius",
      "posterPath": "https://image.tmdb.org/t/p/w500//6JjfSchsU6daXk2AKX8EEBjO3Fm.jpg",
      "releaseDate": "2022-03-30",
      "voteAverage": 6.4,
      "voteCount": 1223,
      "mediaType": "movie",
      "id": 526896,
      "adult": false,
      "overview": "Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.",
      "title": "Morbius",
      "video": false,
      "popularity": 6531.767
    }
  ]
};

describe('MoviesListComponent', () => {
  let component: MoviesListComponent;
  let fixture: ComponentFixture<MoviesListComponent>;
  let moviesServiceSpy = jasmine.createSpyObj('MoviesService', ['getPopular']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MoviesListComponent,
        MovieCardComponent,
        MatCard,
        MatCardActions,
        MatCardContent,
        MatCardTitle],
      providers: [
        { provide: MoviesService, useValue: moviesServiceSpy }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MoviesListComponent);
    component = fixture.componentInstance;
    moviesServiceSpy.getPopular.and.returnValue(of({}));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a movies paragraph', () => {
    const paragraph = fixture.debugElement.query(By.css('p')).nativeElement;
    expect(paragraph.textContent).toBe('Popular movies right now');
    expect(paragraph.classList).toContain('list-message');
  })

  it('should not have movies before ngOnInit', () => {
    expect(component.movies.length).withContext('should not have movies before ngOnInit')
      .toBe(0);
  })

  it('should have movies properly parsed after ngOnInit', () => {
    moviesServiceSpy.getPopular.and.returnValue(of(mockResponse));
    component.ngOnInit();
    expect(component.movies).toEqual(mockResponse.results);
  })

  it('should display movie card', () => {
    moviesServiceSpy.getPopular.and.returnValue(of(mockResponse));
    component.ngOnInit();
    fixture.detectChanges();
    const cards = fixture.nativeElement.querySelectorAll('app-movie-card');
    expect(cards.length).withContext('should display a single movie card').toBe(1);
  })

  it('should catch an error and return default response', () => {
    moviesServiceSpy.getPopular.and.returnValue(throwError(() => new Error('Something bad')))
    component.ngOnInit();
    fixture.detectChanges();
    expect(component.movies).toEqual([]);
  })
});
