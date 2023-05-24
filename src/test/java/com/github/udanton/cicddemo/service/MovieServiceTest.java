package com.github.udanton.cicddemo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.udanton.cicddemo.persistence.MovieRepository;
import com.github.udanton.cicddemo.persistence.entity.Genre;
import com.github.udanton.cicddemo.persistence.entity.Movie;
import com.github.udanton.cicddemo.persistence.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository);
    }

    @Test
    public void testGetAllMovies() {
        // Arrange
        List<Movie> movies = Arrays.asList(
            new Movie(1L, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD)),
            new Movie(2L, "Movie 2", Genre.COMEDY, Arrays.asList(Tag.HAPPY, Tag.EXCITED))
        );
        when(movieRepository.findAll()).thenReturn(movies);

        // Act
        List<Movie> result = movieService.getAllMovies();

        // Assert
        assertEquals(movies.size(), result.size());
        assertEquals(movies, result);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void testGetMovieById() {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie(movieId, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Act
        Movie result = movieService.getMovieById(movieId);

        // Assert
        assertEquals(movie, result);
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void testCreateMovie() {
        // Arrange
        Movie movie = new Movie(null, "New Movie", Genre.DRAMA, Arrays.asList(Tag.SAD));

        // Act
        Movie savedMovie = new Movie(1L, "New Movie", Genre.DRAMA, Arrays.asList(Tag.SAD));
        when(movieRepository.save(movie)).thenReturn(savedMovie);
        Movie result = movieService.createMovie(movie);

        // Assert
        assertEquals(savedMovie, result);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testUpdateMovie() {
        // Arrange
        Movie movie = new Movie(1L, "Updated Movie", Genre.COMEDY, Arrays.asList(Tag.HAPPY));

        // Act
        Movie updatedMovie = new Movie(1L, "Updated Movie", Genre.COMEDY, Arrays.asList(Tag.HAPPY));
        when(movieRepository.save(movie)).thenReturn(updatedMovie);
        Movie result = movieService.updateMovie(movie);

        // Assert
        assertEquals(updatedMovie, result);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void testDeleteMovie() {
        // Arrange
        Long movieId = 1L;

        // Act
        movieService.deleteMovie(movieId);

        // Assert
        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @Test
    public void testFilterMoviesByGenre() {
        // Arrange
        Genre genre = Genre.ACTION;
        List<Movie> movies = Arrays.asList(
            new Movie(1L, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD)),
            new Movie(2L, "Movie 2", Genre.COMEDY, Arrays.asList(Tag.HAPPY, Tag.EXCITED))
        );
        when(movieRepository.findByGenre(genre)).thenReturn(movies);

        // Act
        List<Movie> result = movieService.filterMoviesByGenre(genre);

        // Assert
        assertEquals(movies.size(), result.size());
        assertEquals(movies, result);
        verify(movieRepository, times(1)).findByGenre(genre);
    }

}
