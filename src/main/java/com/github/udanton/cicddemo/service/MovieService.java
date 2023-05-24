package com.github.udanton.cicddemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.github.udanton.cicddemo.persistence.MovieRepository;
import com.github.udanton.cicddemo.persistence.entity.Genre;
import com.github.udanton.cicddemo.persistence.entity.Movie;
import com.github.udanton.cicddemo.persistence.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public List<Movie> filterMoviesByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getRandomMovies(int count) {
        List<Movie> allMovies = movieRepository.findAll();
        int totalMovies = allMovies.size();
        if (count >= totalMovies) {
            return allMovies;
        }
        List<Movie> randomMovies = new ArrayList<>();
        Random random = new Random();
        while (randomMovies.size() < count) {
            int index = random.nextInt(totalMovies);
            Movie movie = allMovies.get(index);
            if (!randomMovies.contains(movie)) {
                randomMovies.add(movie);
            }
        }
        return randomMovies;
    }

    public List<Movie> filterMoviesByTags(List<Tag> tags) {
        return movieRepository.findAll().stream()
            .filter(movie -> movie.getTags().containsAll(tags))
            .collect(Collectors.toList());
    }
}
