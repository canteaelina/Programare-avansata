package org.example.database;

import java.util.Date;

public class Movie {
    private int id;
    private String title;
    private Date releaseDate;
    private int duration;
    private double score;
    private Integer genreId;

    public Movie(int id, String title, Date releaseDate, int duration, double score, Integer genreId) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.score = score;
        this.genreId = genreId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public double getScore() {
        return score;
    }
}
