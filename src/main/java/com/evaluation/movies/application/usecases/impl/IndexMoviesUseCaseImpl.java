package com.evaluation.movies.application.usecases.impl;

import com.evaluation.movies.application.ports.outbound.IndexMoviesPort;
import com.evaluation.movies.application.usecases.IndexMoviesUseCase;

/**
 * Implementation of the IndexMoviesUseCase.
 *
 * This use case handles the business logic for indexing movies by delegating
 * the actual persistence or search engine interaction to the IndexMoviesPort.
 */
public class IndexMoviesUseCaseImpl implements IndexMoviesUseCase {

    private final IndexMoviesPort indexMoviesPort;

    /**
     * Constructor for dependency injection.
     *
     * @param indexMoviesPort the port that handles movie indexing
     */
    public IndexMoviesUseCaseImpl(IndexMoviesPort indexMoviesPort) {
        this.indexMoviesPort = indexMoviesPort;
    }

    /**
     * Index all movies.
     *
     * This method delegates the indexing process to the IndexMoviesPort.
     * It can be extended to include additional business logic if needed.
     */
    public void indexMovies() {
        indexMoviesPort.indexMovies();
    }
}

