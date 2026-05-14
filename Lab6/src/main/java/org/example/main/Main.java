package org.example.main;

import org.example.dao.ActorDAO;
import org.example.dao.GenreDAO;
import org.example.dao.MovieActorDAO;
import org.example.dao.MovieDAO;
import org.example.database.*;
import org.example.raport.GeneratorRaport;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        try {
            GenreDAO genreDAO = new GenreDAO();
            ActorDAO actorDAO = new ActorDAO();
            MovieDAO movieDAO = new MovieDAO();
            MovieActorDAO movieActorDAO = new MovieActorDAO();

            if (genreDAO.findByName("Sci-Fi") == null) {
                genreDAO.create(new Genre(0, "Sci-Fi"));
            }
            Genre sciFi = genreDAO.findByName("Sci-Fi");
            int genreId = sciFi.getId();

            if (genreDAO.findByName("Action") == null) {
                genreDAO.create(new Genre(1, "Action"));
            }
            Genre action = genreDAO.findByName("Action");
            int genreId2 = action.getId();

            Movie movie = new Movie(1, "Fight Club2", new Date(), 160, 9.5, genreId2);
            movieDAO.create(movie);

            if (actorDAO.findByName("Tom Holland") == null) {
                actorDAO.create(new Actor(0, "Tom Holland"));
            }
            Actor actor = actorDAO.findByName("Tom Holland");
            int actorId = actor.getId();

            try {
                movieActorDAO.addActorToMovie(1, actorId);
            } catch (SQLException e) {
                System.out.println("Legatura actor-film exista deja. Trecem mai departe...");
            }

            System.out.println("Datele au fost inserate folosind DAO-urile generice, modelele POJO și HikariCP!");

            GeneratorRaport.generateHTML("report.html");

        } catch (SQLException e) {
            System.err.println("Eroare SQL: " + e.getMessage());
        } finally {
            Database.closeDataSource();
        }
    }

       /*
        try {
            GenreDAO genres = new GenreDAO();

            genres.create("Action");
            genres.create("Drama");
            genres.create("Comedy");

            genres.create("Horror1");
            genres.create("Thriller2");

            Integer actionId = genres.findByName("Action");
            System.out.println("ID pt 'Action' = " + actionId);

            if (actionId != null) {
                String genreName = genres.findById(actionId);
                System.out.println("ID " + actionId + " = " + genreName);
            }

            Integer thrillerId = genres.findByName("Thriller");
            System.out.println("ID pt 'Thriller' = " + thrillerId);

            if (thrillerId != null) {
                String genreName = genres.findById(thrillerId);
                System.out.println("ID " + thrillerId + " = " + genreName);
            }

        } catch (SQLException e) {
            System.err.println("Eroare SQL: " + e.getMessage());
        } finally {
            Database.closeConnection();
        }
    }
        */
}