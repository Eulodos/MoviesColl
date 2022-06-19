import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { MovieModelPageable } from './model/movie-pageable.model';
import { MoviesService } from './movies.service';



describe('MoviesService', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let service: MoviesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        MoviesService
      ]
    });
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(MoviesService);
  });

  afterEach(() => {
    httpTestingController.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('#getPopular', () => {
    let expectedMovies: MovieModelPageable;

    beforeEach(() => {
      service = TestBed.inject(MoviesService);
      expectedMovies = {
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
      } as MovieModelPageable;
    })

    it('should fetch movies', () => {
      service.getPopular().subscribe({
        next: results => expect(results).withContext('should return expected movies').toEqual(expectedMovies),
        error: fail
      });

      const req = httpTestingController.expectOne(`${service.url}/movies/popular`);
      expect(req.request.method).toEqual('GET');

      req.flush(expectedMovies);
    })

    it('should return expected movies when called multiple times', () => {
      service.getPopular().subscribe();
      service.getPopular().subscribe();
      service.getPopular().subscribe({
        next: movies => expect(movies).withContext('should return expected movies').toEqual(expectedMovies),
        error: fail
      });

      const requests = httpTestingController.match(`${service.url}/movies/popular`);
      expect(requests.length).withContext('getPopular() invocations').toEqual(3);

      requests[0].flush({});
      requests[1].flush({ "total_results": 0, "totalPages": 0, "page": 1, "results": [] });
      requests[2].flush(expectedMovies);
    })

    it('should throw an error if the request fails', () => {
      const status = 500;
      const statusText = 'Server error';
      const errorEvent = new ProgressEvent('API error');

      let actualError: HttpErrorResponse | undefined;

      service.getPopular().subscribe({
        next: () => fail('next handler must not be called'),
        error: error => actualError = error,
        complete: () => fail('complete handler must not be called')
      }
      );

      httpTestingController.expectOne(`${service.url}/movies/popular`).error(
        errorEvent,
        { status, statusText }
      );

      if (!actualError) {
        throw new Error('Error needs to be defined');
      }
      expect(actualError.message).toEqual('Something bad happened; please try again later.');
    })

    it('should throw an error if the request fails with status 0', () => {
      const status = 0;
      const statusText = 'Server error';
      const errorEvent = new ProgressEvent('API error');

      let actualError: HttpErrorResponse | undefined;

      service.getPopular().subscribe({
        next: () => fail('next handler must not be called'),
        error: error => actualError = error,
        complete: () => fail('complete handler must not be called')
      }
      );

      httpTestingController.expectOne(`${service.url}/movies/popular`).error(
        errorEvent,
        { status, statusText }
      );

      if (!actualError) {
        throw new Error('Error needs to be defined');
      }
      expect(actualError.message).toEqual('Something bad happened; please try again later.');
    })
  })
});
