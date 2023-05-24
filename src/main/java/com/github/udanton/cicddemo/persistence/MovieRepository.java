package com.github.udanton.cicddemo.persistence;

import java.util.List;

import com.github.udanton.cicddemo.persistence.entity.Genre;
import com.github.udanton.cicddemo.persistence.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenre(Genre genre);
}
