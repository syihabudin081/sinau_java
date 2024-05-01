package com.example.BioskopXXL.service;


import com.example.BioskopXXL.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAllMovies();
    Optional <Movie> getMovieById(int id);
    Movie addMovie(Movie movie);

}
