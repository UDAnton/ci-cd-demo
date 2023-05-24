package com.github.udanton.cicddemo.api;

import java.util.List;

import com.github.udanton.cicddemo.persistence.entity.Genre;
import com.github.udanton.cicddemo.persistence.entity.Movie;
import com.github.udanton.cicddemo.persistence.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private Genre genre;
    private List<Tag> tags;
}
