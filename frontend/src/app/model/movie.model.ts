export interface MovieModel {
    id: number,
    adult: boolean,
    backdropPath: string,
    genreIds: number[],
    originalLanguage: string,
    originalTitle: string,
    overview: string,
    posterPath: string,
    releaseDate: string,
    title: string,
    video: boolean,
    voteAverage: number,
    voteCount: number,
    popularity: number,
    mediaType: string
}