package com.evaluation.movies.infrastructure.adapters.outbound.movie;

import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.entity.MovieEntity;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.mappers.MovieEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryAdapterTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @Mock
    private MovieEntityMapper movieEntityMapper;

    @InjectMocks
    private MovieRepositoryAdapter movieRepositoryAdapter;

    @Mock
    private SearchHit<MovieEntity> hit;

    @Mock
    private SearchHits<MovieEntity> searchHits;

    @Test
    void shouldReturnMoviesWhenTitleMatches() {
        //Arrange
        String title = "Matrix";
        MovieEntity movieEntity = new MovieEntity("tt123", "Matrix", 1999);
        Movie movieDomain = new Movie("tt123", "Matrix", 1999);

        doReturn(movieEntity).when(hit).getContent();
        doReturn(Stream.of(hit)).when(searchHits).stream();
        doReturn(searchHits).when(elasticsearchOperations).search(any(Query.class), eq(MovieEntity.class));
        doReturn(List.of(movieDomain)).when(movieEntityMapper).toDomains(List.of(movieEntity));
        // Act
        List<Movie> result = movieRepositoryAdapter.findByTitle(title);

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Matrix", result.get(0).getTitle());
        assertEquals(1999, result.get(0).getYear());
    }

    @Test
    void shouldReturnMoviesWhenYearMatches() {

        //Arrange
        Integer year = 1999;
        MovieEntity movieEntity = new MovieEntity("tt123", "Matrix", year);
        Movie movieDomain = new Movie("tt123", "Matrix", year);

        doReturn(movieEntity).when(hit).getContent();
        doReturn(Stream.of(hit)).when(searchHits).stream();
        doReturn(searchHits).when(elasticsearchOperations)
                .search(any(Query.class), eq(MovieEntity.class));
        doReturn(List.of(movieDomain)).when(movieEntityMapper)
                .toDomains(List.of(movieEntity));

        // Act
        List<Movie> result = movieRepositoryAdapter.findByYear(year);

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Matrix", result.get(0).getTitle());
        assertEquals(year, result.get(0).getYear());
    }

    @Test
    void shouldReturnMoviesWhenTitleAndYearMatch() {
        String title = "Matrix";
        Integer year = 1999;

        MovieEntity movieEntity = new MovieEntity("tt123", title, year);
        Movie movieDomain = new Movie("tt123", title, year);

        doReturn(movieEntity).when(hit).getContent();
        doReturn(Stream.of(hit)).when(searchHits).stream();
        doReturn(searchHits).when(elasticsearchOperations)
                .search(any(Query.class), eq(MovieEntity.class));
        doReturn(List.of(movieDomain)).when(movieEntityMapper)
                .toDomains(List.of(movieEntity));
        // Act
        List<Movie> result = movieRepositoryAdapter.findByTitleAndYear(title, year);

        //Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(title, result.get(0).getTitle());
        assertEquals(year, result.get(0).getYear());
    }

    @Test
    void shouldSaveAllMoviesSuccessfully() {
        Movie movie = new Movie("tt123", "Matrix", 1999);
        MovieEntity movieEntity = new MovieEntity("tt123", "Matrix", 1999);

        when(movieEntityMapper.toEntities(List.of(movie))).thenReturn(List.of(movieEntity));

        when(elasticsearchOperations.save(List.of(movieEntity))).thenReturn(List.of(movieEntity));

        // Act
        movieRepositoryAdapter.saveAll(List.of(movie));

        //Assert
        verify(movieEntityMapper, times(1)).toEntities(List.of(movie));
        verify(elasticsearchOperations, times(1)).save(List.of(movieEntity));
    }



}
