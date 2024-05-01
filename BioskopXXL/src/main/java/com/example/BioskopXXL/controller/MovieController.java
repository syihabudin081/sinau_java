// MovieController.java
package com.example.BioskopXXL.controller;

import com.example.BioskopXXL.model.Movie;
import com.example.BioskopXXL.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, Welcome To BioskopXXL!");
    }
3
    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.getAllMovies();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(movies);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestBody Movie newMovie) {
        Movie savedMovie = movieService.addMovie(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie '" + savedMovie.getTitle() + "' added successfully!");
    }

}
