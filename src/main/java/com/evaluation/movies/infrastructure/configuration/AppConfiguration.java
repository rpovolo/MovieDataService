package com.evaluation.movies.infrastructure.configuration;

import com.evaluation.movies.application.ports.outbound.*;
import com.evaluation.movies.application.usecases.*;
import com.evaluation.movies.application.usecases.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public IndexMoviesUseCase indexMoviesUseCase(IndexMoviesPort indexMoviesPort) {
        return new IndexMoviesUseCaseImpl(indexMoviesPort);
    }

}
