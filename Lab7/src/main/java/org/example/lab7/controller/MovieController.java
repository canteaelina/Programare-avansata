package org.example.lab7.controller;

import org.example.lab7.dao.MovieDAO;
import org.example.lab7.database.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieDAO movieDAO = new MovieDAO();

    @GetMapping
    public List<Movie> getAllMovies() {
        try {
            return movieDAO.findAll();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // POST: Adăugarea unui film nou
    @PostMapping
    public ResponseEntity<String> addMovie(@RequestBody Movie movie) {
        try {
            movieDAO.create(movie);
            return new ResponseEntity<>("Filmul a fost adaugat cu succes!", HttpStatus.CREATED);
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la adaugarea filmului", e);
        }
    }

    // PUT: Modificarea tuturor proprietăților
    @PutMapping("/{id}")
    public ResponseEntity<String> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        try {
            movieDAO.update(id, movie);
            return ResponseEntity.ok("Filmul a fost actualizat!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea filmului", e);
        }
    }

    // PATCH: Modificarea doar a scorului
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateMovieScore(@PathVariable int id, @RequestParam double score) {
        try {
            movieDAO.updateScore(id, score);
            return ResponseEntity.ok("Scorul filmului a fost actualizat!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea scorului", e);
        }
    }

    // DELETE: Ștergerea unui film
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        try {
            movieDAO.delete(id);
            return ResponseEntity.ok("Filmul a fost sters!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea filmului", e);
        }
    }
}
