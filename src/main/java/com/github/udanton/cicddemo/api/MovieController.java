package com.github.udanton.cicddemo.api;

import java.util.List;
import java.util.stream.Collectors;

import com.github.udanton.cicddemo.persistence.entity.Movie;
import com.github.udanton.cicddemo.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        List<MovieDTO> movieDTOs = movies.stream()
            .map(movieMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(movieDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        MovieDTO movieDTO = movieMapper.toDTO(movie);
        return ResponseEntity.ok(movieDTO);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        Movie movie = movieMapper.toEntity(movieDTO);
        Movie createdMovie = movieService.createMovie(movie);
        MovieDTO createdMovieDTO = movieMapper.toDTO(createdMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovieDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        Movie existingMovie = movieService.getMovieById(id);
        if (existingMovie == null) {
            return ResponseEntity.notFound().build();
        }
        Movie movie = movieMapper.toEntity(movieDTO);
        movie.setId(id);
        Movie updatedMovie = movieService.updateMovie(movie);
        MovieDTO updatedMovieDTO = movieMapper.toDTO(updatedMovie);
        return ResponseEntity.ok(updatedMovieDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
