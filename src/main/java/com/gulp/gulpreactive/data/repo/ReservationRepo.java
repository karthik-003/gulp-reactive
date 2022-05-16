package com.gulp.gulpreactive.data.repo;


import com.gulp.gulpreactive.data.model.Reservation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReservationRepo extends ReactiveMongoRepository<Reservation,String> {

    Flux<Reservation> findByName(String name);


}
