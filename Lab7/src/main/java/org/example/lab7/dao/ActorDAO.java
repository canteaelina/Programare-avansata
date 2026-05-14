package org.example.lab7.dao;


import org.example.lab7.database.Actor;
import org.example.lab7.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO implements GenericDAO<Actor> {
    @Override
    public void create(Actor actor) throws SQLException {
        String sql = "INSERT INTO actors (name) VALUES (?)";
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, actor.getName()); // Modificat aici
            pstmt.executeUpdate();
        }
    }

    @Override
    public Actor findById(int id) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM actors WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Actor(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    @Override
    public List<Actor> findAll() throws SQLException {
        List<Actor> list = new ArrayList<>();
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM actors")) {
            while (rs.next()) list.add(new Actor(rs.getInt("id"), rs.getString("name")));
        }
        return list;
    }

    public Actor findByName(String name) throws SQLException {
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM actors WHERE name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Actor(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }
}
