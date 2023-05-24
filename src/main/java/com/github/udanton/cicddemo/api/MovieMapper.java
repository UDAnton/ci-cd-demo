package com.github.udanton.cicddemo.api;

import com.github.udanton.cicddemo.persistence.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setTags(movie.getTags());
        return dto;
    }

    public Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setTags(dto.getTags());
        return movie;
    }

}
