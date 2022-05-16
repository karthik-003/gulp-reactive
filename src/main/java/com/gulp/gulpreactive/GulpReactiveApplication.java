package com.gulp.gulpreactive;

import com.gulp.gulpreactive.data.model.Book;
import com.gulp.gulpreactive.data.model.Reservation;
import com.gulp.gulpreactive.data.repo.BookRepo;
import com.gulp.gulpreactive.data.repo.ReservationRepo;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EntityScan("com.gulp.data.model")
@EnableR2dbcRepositories
public class GulpReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulpReactiveApplication.class, args);
	}

//	@Bean
//	ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory factory){
//		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//		initializer.setConnectionFactory(factory);
//		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
//		return initializer;
//	}

}

@Component
@RequiredArgsConstructor
class SampleDataInitializer {
	private final ReservationRepo reservationRepo;

	Logger log = LoggerFactory.getLogger(SampleDataInitializer.class);

	@EventListener(ApplicationReadyEvent.class)
	public void ready(){
		Flux<Reservation> names = Flux.just("Rocky", "Virat", "Dhoni", "Karthik")
				.map(name -> new Reservation(null,name))
				.flatMap(r->this.reservationRepo.save(r));
		this.reservationRepo
				.deleteAll() // Delete ALL records initially.
				.thenMany(names) //
				.thenMany(this.reservationRepo.findByName("Dhoni"))
				.subscribe(e->log.info(e.toString()));



	}
}


@Component
@RequiredArgsConstructor
class H2DataInitializer {
	 private final BookRepo bookRepo;

	 Logger log = LoggerFactory.getLogger(H2DataInitializer.class);

	 @EventListener(ApplicationReadyEvent.class)
	public void ready(){
	 	Flux<Book> books = Flux.just("A Girl with Seven Names","IKIGAI","1984","Catch 22")
				.map(title -> new Book(null,title,null))
				.flatMap(b->this.bookRepo.save(b));
		books.subscribe();
		this.bookRepo.findByTitle("IKIGAI").subscribe(b->log.info(b.toString()));
	 }

}
