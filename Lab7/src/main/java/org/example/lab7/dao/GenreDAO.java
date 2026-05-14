package org.example.lab7.dao;


import org.example.lab7.database.Database;
import org.example.lab7.database.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements GenericDAO<Genre> {

    @Override
    public void create(Genre genre) throws SQLException {
        String sql = "INSERT INTO genres (name) VALUES (?)";
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, genre.getName());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Genre findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM genres WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Genre(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    @Override
    public List<Genre> findAll() throws SQLException {
        List<Genre> list = new ArrayList<>();
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM genres")) {
            while (rs.next()) list.add(new Genre(rs.getInt("id"), rs.getString("name")));
        }
        return list;
    }

    public Genre findByName(String name) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM genres WHERE name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Genre(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

}
