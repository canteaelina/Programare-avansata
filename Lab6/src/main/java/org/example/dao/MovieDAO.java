package org.example.dao;

import org.example.database.Database;
import org.example.database.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements GenericDAO<Movie> {
    @Override
    public void create(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, release_date, duration, score, genre_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle()); // Modificat aici
            pstmt.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
            pstmt.setInt(3, movie.getDuration());
            pstmt.setDouble(4, movie.getScore());
            if (movie.getGenreId() != null) pstmt.setInt(5, movie.getGenreId());
            else pstmt.setNull(5, Types.INTEGER);
            pstmt.executeUpdate();
        }
    }

    @Override
    public Movie findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM movies WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Movie(rs.getInt("id"), rs.getString("title"),
                        rs.getDate("release_date"), rs.getInt("duration"),
                        rs.getDouble("score"), rs.getInt("genre_id"));
            }
        }
        return null;
    }

    @Override
    public List<Movie> findAll() throws SQLException {
        List<Movie> list = new ArrayList<>();
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {
            while (rs.next()) {
                list.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getDate("release_date"),
                        rs.getInt("duration"), rs.getDouble("score"), rs.getInt("genre_id")));
            }
        }
        return list;
    }
}
