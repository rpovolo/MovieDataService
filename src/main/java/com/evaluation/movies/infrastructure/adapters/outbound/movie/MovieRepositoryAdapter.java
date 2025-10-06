package com.evaluation.movies.infrastructure.adapters.outbound.movie;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import com.evaluation.movies.domain.model.Movie;
import com.evaluation.movies.domain.repository.MovieRepositoryPort;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.entity.MovieEntity;
import com.evaluation.movies.infrastructure.adapters.outbound.movie.mappers.MovieEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
public class MovieRepositoryAdapter implements MovieRepositoryPort {

    private final ElasticsearchOperations elasticsearchOperations;
    private final MovieEntityMapper movieEntityMapper;

    /**
     * Constructor for dependency injection.
     *
     * @param elasticsearchOperations the operations interface for Elasticsearch
     * @param movieEntityMapper       mapper to convert between Movie and MovieEntity
     */
    public MovieRepositoryAdapter(ElasticsearchOperations elasticsearchOperations, MovieEntityMapper movieEntityMapper) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.movieEntityMapper = movieEntityMapper;
    }

    /**
     * Find movies by title using a match query.
     *
     * @param title the title to search for
     * @return list of movies matching the title
     */
    @Override
    public List<Movie> findByTitle(String title) {
        var query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m
                        .field("title")
                        .query(title)
                ))
                .build();

        SearchHits<MovieEntity> hits = elasticsearchOperations.search(query, MovieEntity.class);
        List<MovieEntity> entities = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return movieEntityMapper.toDomains(entities);
    }

    /**
     * Find movies by year using a term query.
     *
     * @param year the year to search for
     * @return list of movies from the given year
     */
    @Override
    public List<Movie> findByYear(Integer year) {
        var query = NativeQuery.builder()
                .withQuery(q ->
                        q.term(t -> t.field("year").value(year)))
                .build();

        SearchHits<MovieEntity> hits = elasticsearchOperations.search(query, MovieEntity.class);
        List<MovieEntity> entities = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return movieEntityMapper.toDomains(entities);
    }

    /**
     * Find movies by both title and year using a boolean query.
     *
     * @param title the title to search for
     * @param year  the year to search for
     * @return list of movies matching both criteria
     */
    @Override
    public List<Movie> findByTitleAndYear(String title, Integer year) {
        var query = NativeQuery.builder()
                .withQuery((Function<Query.Builder, ObjectBuilder<Query>>) q ->
                        q.bool(b -> b
                                .must(m -> m.matchPhrase(mp -> mp.field("title").query(title)))
                                .must(m -> m.term(t -> t.field("year").value(year)))
                        ))
                .build();

        SearchHits<MovieEntity> hits = elasticsearchOperations.search(query, MovieEntity.class);
        List<MovieEntity> entities = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return movieEntityMapper.toDomains(entities);
    }

    /**
     * Retrieve all movies.
     *
     * @return list of all movies in the index
     */
    @Override
    public List<Movie> findAll() {

        var query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .build();

        SearchHits<MovieEntity> hits = elasticsearchOperations.search(query, MovieEntity.class);

        List<MovieEntity> entities = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return movieEntityMapper.toDomains(entities);
    }

    /**
     * Save a list of movies to Elasticsearch.
     *
     * @param movies the list of movies to save
     */
    @Override
    public void saveAll(List<Movie> movies) {
        List<MovieEntity> movieEntities = movieEntityMapper.toEntities(movies);

        try {
            elasticsearchOperations.save(movieEntities);
            log.info("Saved {} movies to Elasticsearch", movieEntities.size());
        } catch (Exception e) {
            log.error("Failed to save movies to Elasticsearch: {}", e.getMessage(), e);
            throw e;
        }
    }
}
