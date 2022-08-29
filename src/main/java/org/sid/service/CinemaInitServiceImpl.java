package org.sid.service;


import org.sid.dao.*;
import org.sid.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {

    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SceanceRepository sceanceRepository;
    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void initVilles() {
       Stream.of("Casa","Dakar","Rabat","Marrakech").forEach(v->{
           Ville ville = new Ville();
           ville.setName(v);
           villeRepository.save(ville);
       });


    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(vil->{
            Stream.of("Canal Olympia","Mega Rama","Chahrazad")
                    .forEach(nameCinema->{
                        Cinema cinema = new Cinema();
                        cinema.setName(nameCinema);
                        cinema.setNombreSalles(3+(int)Math.random()*7);
                        cinema.setVille(vil);
                        cinemaRepository.save(cinema);
                    });
        });

    }

    @Override

    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles();i++) {
                Salle salle = new Salle();
                salle.setName("Salle"+(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlaces(15+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });

    }
    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for (int i = 0; i < salle.getNombrePlaces();i++) {
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }

        });

    }

    @Override
    public void initSceances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00","15:00","16:00","17:00","21:00").forEach(
                s->{
                    Sceance sceance = new Sceance();
                    try {
                        sceance.setHeureDebut(dateFormat.parse(s));
                        sceanceRepository.save(sceance);

                    }catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        );

    }


    @Override
    public void initCategories() {
        Stream.of("Histoire","Fiction","Drama").forEach(
                c->{
                    Categorie categorie = new Categorie();
                    categorie.setName(c);
                    categorieRepository.save(categorie);
                });
    }

    @Override
    public void initfilms() {
        List<Categorie> categorie = categorieRepository.findAll();
        double[] durees = new double[] {1,1.5,2,2.5,3,};
        Stream.of("50 nuances de grey","Confessions","On sort pour la photo","rouge").forEach(
                films -> {
                    Film film = new Film();
                    film.setTitre(films);
                    film.setDuree(durees[new Random().nextInt(durees.length)]);
                    film.setPhoto(films);
                    film.setCategorie(categorie.get(new Random().nextInt(categorie.size())));
                    filmRepository.save(film);

                }
        );

    }



    @Override
    public void initProjectionFilms() {
        double[] prices = new double[] {30,40,50,10,90,100};
        villeRepository.findAll().forEach(ville->{
           ville.getCinemas().forEach(cinema -> {
               cinema.getSalles().forEach(
                       salle -> {
                           filmRepository.findAll().forEach(films -> {
                               sceanceRepository.findAll().forEach(sceance -> {
                                   ProjectionFilm projection = new ProjectionFilm();
                                   projection.setDateProjection(new Date());
                                   projection.setFilm(films);
                                   projection.setPrix(prices[new Random().nextInt(prices.length)]);
                                   projectionFilmRepository.save(projection);
                               });
                           });
                       }
               );
           });
        });

    }

    @Override
    public void initTickets() {
        projectionFilmRepository.findAll().forEach(
                p->{
                    p.getSalle().getPlaces().forEach(place -> {
                        Ticket ticket = new Ticket();
                        ticket.setPlace(place);
                        ticket.setPrix(p.getPrix());
                        ticket.setProjectionFilm(p);
                        ticket.setReservee(false);
                        ticketRepository.save(ticket);
                    });
                });

    }

}
