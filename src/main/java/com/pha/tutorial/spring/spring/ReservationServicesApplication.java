package com.pha.tutorial.spring.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServicesApplication {

	@Bean
	CommandLineRunner commandLineRunner (ReservationRepository reservationRepository) {
		return strings -> {
			Stream.of("Josh", "Pieter", "Tasha", "Eric", "Susie", "Max")
					.forEach(s -> reservationRepository.save(new Reservation(s)));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ReservationServicesApplication.class, args);
	}

}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
	@RestResource(path = "by-name")
	Collection<Reservation> findByName(@Param("rn") String name);
}

@Entity
class Reservation {

	@Id @GeneratedValue
	private Long id;
	private String name;

	public Reservation() {
	}

	public Reservation(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Reservation{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}