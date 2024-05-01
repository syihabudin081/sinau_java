package com.example.BioskopXXL.service;

import com.example.BioskopXXL.model.Movie;
import com.example.BioskopXXL.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getMovieById(int id) {
        return Optional.ofNullable(movieRepository.findById(id));
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
