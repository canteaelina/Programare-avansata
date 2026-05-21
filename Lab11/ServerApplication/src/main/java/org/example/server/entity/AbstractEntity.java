package org.example.server.entity;

import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Se apelează automat înainte de primul INSERT în baza de date
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Se apelează automat înainte de orice UPDATE în baza de date
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    // Getteri și Setteri
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
}