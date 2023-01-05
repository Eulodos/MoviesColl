import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCard, MatCardActions, MatCardContent, MatCardImage, MatCardTitle } from '@angular/material/card';
import { By } from '@angular/platform-browser';
import { MovieModel } from 'src/app/model/movie.model';
import { MovieCardComponent } from './movie-card.component';


describe('MovieCardComponent', () => {
  let component: MovieCardComponent;
  let fixture: ComponentFixture<MovieCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MovieCardComponent,
        MatCard,
        MatCardImage,
        MatCardTitle,
        MatCardContent,
        MatCardActions]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieCardComponent);
    const movie: MovieModel = {
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
      "voteAverage": 6.3,
      "voteCount": 1125,
      "mediaType": "movie",
      "id": 526896,
      "adult": false,
      "overview": "Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.",
      "title": "Morbius",
      "video": false,
      "popularity": 11236.546
    };
    component = fixture.componentInstance;
    component.movie = movie;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a title', () => {
    const title = fixture.debugElement.query(By.css('mat-card-title div')).nativeElement;
    expect(title.textContent).toContain('Morbius');
  })

  it('should have movie release date', () => {
    const cardContent = fixture.debugElement.query(By.css('mat-card-content')).nativeElement;
    expect(cardContent.textContent).toBe('30 Mar, 2022');
  })

  it('should have buttons with movie related actions', () => {
    const buttons = fixture.debugElement.queryAll(By.css('mat-card-actions button'));
    expect(buttons[0].nativeElement.textContent).toBe('Details');
    expect(buttons[1].nativeElement.textContent).toBe('To watchlist');
    expect(buttons[2].nativeElement.textContent).toBe('Like');
  })
});
