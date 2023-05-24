package com.github.udanton.cicddemo.api;

import java.util.Arrays;
import java.util.List;

import com.github.udanton.cicddemo.persistence.entity.Genre;
import com.github.udanton.cicddemo.persistence.entity.Movie;
import com.github.udanton.cicddemo.persistence.entity.Tag;
import com.github.udanton.cicddemo.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private MovieMapper movieMapper;

    @Test
    public void testGetAllMovies() throws Exception {
        // Arrange
        List<Movie> movies = Arrays.asList(
            new Movie(1L, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD)),
            new Movie(2L, "Movie 2", Genre.COMEDY, Arrays.asList(Tag.HAPPY, Tag.EXCITED))
        );
        when(movieService.getAllMovies()).thenReturn(movies);
        when(movieMapper.toDTO(movies.get(0))).thenReturn(toDTO(movies.get(0)));
        when(movieMapper.toDTO(movies.get(1))).thenReturn(toDTO(movies.get(1)));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/movies")
            .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(movies.size()))
            .andExpect(jsonPath("$[0].id").value(movies.get(0).getId()))
            .andExpect(jsonPath("$[0].title").value(movies.get(0).getTitle()))
            .andExpect(jsonPath("$[0].genre").value(movies.get(0).getGenre().toString()))
            .andExpect(jsonPath("$[0].tags.length()").value(movies.get(0).getTags().size()))
            .andExpect(jsonPath("$[1].id").value(movies.get(1).getId()))
            .andExpect(jsonPath("$[1].title").value(movies.get(1).getTitle()))
            .andExpect(jsonPath("$[1].genre").value(movies.get(1).getGenre().toString()))
            .andExpect(jsonPath("$[1].tags.length()").value(movies.get(1).getTags().size()));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    public void testGetMovieById() throws Exception {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie(movieId, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD));
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(toDTO(movie));

        // Act
        ResultActions resultActions = mockMvc.perform(get("/movies/{id}", movieId)
            .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(movie.getId()))
            .andExpect(jsonPath("$.title").value(movie.getTitle()))
            .andExpect(jsonPath("$.genre").value(movie.getGenre().toString()))
            .andExpect(jsonPath("$.tags.length()").value(movie.getTags().size()));

        verify(movieService, times(1)).getMovieById(movieId);
    }

    @Test
    public void testCreateMovie() throws Exception {
        // Arrange
        Movie movie = new Movie(null, "New Movie", Genre.DRAMA, Arrays.asList(Tag.SAD));

        // Act
        Movie savedMovie = new Movie(1L, "New Movie", Genre.DRAMA, Arrays.asList(Tag.SAD));
        when(movieMapper.toEntity(any(MovieDTO.class))).thenReturn(movie);
        when(movieService.createMovie(movie)).thenReturn(savedMovie);
        when(movieMapper.toDTO(savedMovie)).thenReturn(toDTO(savedMovie));


        String requestJson = "{\"title\":\"New Movie\",\"genre\":\"DRAMA\",\"tags\":[\"SAD\"]}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movies")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(requestJson);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        // Assert
        resultActions.andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(savedMovie.getId()))
            .andExpect(jsonPath("$.title").value(savedMovie.getTitle()))
            .andExpect(jsonPath("$.genre").value(savedMovie.getGenre().toString()))
            .andExpect(jsonPath("$.tags.length()").value(savedMovie.getTags().size()));

        verify(movieService, times(1)).createMovie(movie);
    }

    @Test
    public void testUpdateMovie() throws Exception {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie(movieId, "Updated Movie", Genre.COMEDY, Arrays.asList(Tag.HAPPY));

        // Act
        Movie updatedMovie = new Movie(movieId, "Updated Movie", Genre.COMEDY, Arrays.asList(Tag.HAPPY));
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(movieMapper.toEntity(any(MovieDTO.class))).thenReturn(movie);
        when(movieService.updateMovie(movie)).thenReturn(updatedMovie);
        when(movieMapper.toDTO(updatedMovie)).thenReturn(toDTO(updatedMovie));

        ResultActions resultActions = mockMvc.perform(put("/movies/{id}", movieId)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Updated Movie\",\"genre\":\"COMEDY\",\"tags\":[\"HAPPY\"]}"));

        // Assert
        resultActions.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(updatedMovie.getId()))
            .andExpect(jsonPath("$.title").value(updatedMovie.getTitle()))
            .andExpect(jsonPath("$.genre").value(updatedMovie.getGenre().toString()))
            .andExpect(jsonPath("$.tags.length()").value(updatedMovie.getTags().size()));

        verify(movieService, times(1)).updateMovie(movie);
    }

    @Test
    public void testDeleteMovie() throws Exception {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie(movieId, "Movie 1", Genre.ACTION, Arrays.asList(Tag.HAPPY, Tag.SAD));
        when(movieService.getMovieById(movieId)).thenReturn(movie);

        // Act
        ResultActions resultActions = mockMvc.perform(delete("/movies/{id}", movieId)
            .contentType(MediaType.APPLICATION_JSON));

        // Assert
        resultActions.andExpect(status().isNoContent());

        verify(movieService, times(1)).deleteMovie(movieId);
    }

    private static MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setTags(movie.getTags());
        return dto;
    }

    public static Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setTags(dto.getTags());
        return movie;
    }

}
