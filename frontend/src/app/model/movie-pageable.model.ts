import { MovieModel } from "./movie.model"

export interface MovieModelPageable {
    page: number,
    results: MovieModel[]
    totalResults: number,
    totalPages: number
}