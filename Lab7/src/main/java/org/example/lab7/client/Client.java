package org.example.lab7.client;

import org.example.lab7.database.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class Client {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate(new JdkClientHttpRequestFactory());;
        String baseUrl = "http://localhost:8050/movies";

        try {
            // GET
            System.out.println("Testare GET");
            ResponseEntity<Movie[]> response = restTemplate.getForEntity(baseUrl, Movie[].class);
            Movie[] movies = response.getBody();
            if (movies != null) {
                for (Movie m : movies) {
                    System.out.println("Gasit film: " + m.getTitle() + " - Scenariu: " + m.getScore());
                }
            }

            // POST
            System.out.println("\nTestare POST");
            Movie newMovie = new Movie(0, "a2313a", new Date(), 148, 8.8, null); // Pune null daca nu ai genre_id
            ResponseEntity<String> postResponse = restTemplate.postForEntity(baseUrl, newMovie, String.class);
            System.out.println("Raspuns POST: " + postResponse.getBody());

            // PUT
            int idToModify = 83;
            Movie updatedMovie = new Movie(idToModify, "Inception Director's Cut", new Date(), 160, 9.0, null);
            restTemplate.put(baseUrl + "/" + idToModify, updatedMovie);
            System.out.println("Filmul a fost modificat (PUT)!");

            // PATCH
            restTemplate.patchForObject(baseUrl + "/" + idToModify + "?score=10", null, String.class);
            System.out.println("Scorul a fost actualizat (PATCH)!");

            // DELETE
            idToModify = 86;
            restTemplate.delete(baseUrl + "/" + idToModify);
            System.out.println("Filmul a fost sters (DELETE)!");

        } catch (Exception e) {
            System.err.println("Eroare in Client: " + e.getMessage());
        }
    }
}
