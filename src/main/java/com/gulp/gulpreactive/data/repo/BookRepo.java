package com.gulp.gulpreactive.data.repo;

import com.gulp.gulpreactive.data.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepo extends ReactiveCrudRepository<Book,Long> {


    Flux<Book> findByTitle(String title);
}
