// MovieRepository.java
package com.example.BioskopXXL.repository;

import com.example.BioskopXXL.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    static List<Movie> movies = new ArrayList<>(List.of(
            new Movie(1, "Inception", 2010),
            new Movie(2, "Interstellar", 2014),
            new Movie(3, "Dunkirk", 2017),
            new Movie(4, "Tenet", 2020),
            new Movie(5, "The Dark Knight", 2008),
            new Movie(6, "The Prestige", 2006),
            new Movie(7, "Memento", 2000)
    ));

    default List<Movie> findAll() {
        return movies;
    }

    default Movie findById(int id) {
        return movies.stream()
                .filter(movie -> movie.getId() == id)
                .findFirst()
                .orElse(null);
    }

    default Movie save(Movie movie) {
        movies.add(movie);
        return movie;
    }
}
