package org.sid;

import org.sid.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionCinemaApplication implements CommandLineRunner {

	@Autowired
	private ICinemaInitService iCinemaInitService;
	public static void main(String[] args) {
		SpringApplication.run(GestionCinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		iCinemaInitService.initCinemas();
		iCinemaInitService.initVilles();
		iCinemaInitService.initCategories();
		iCinemaInitService.initPlaces();
		iCinemaInitService.initSceances();
		iCinemaInitService.films();
		iCinemaInitService.initProjectionFilms();

	}
}
