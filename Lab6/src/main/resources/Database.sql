CREATE TABLE genres (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL UNIQUE
);

CREATE SEQUENCE genres_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER genres_trg
BEFORE INSERT ON genres
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT genres_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE movies (
    id NUMBER PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    release_date DATE,
    duration NUMBER,
    score NUMBER(3, 1),
    genre_id NUMBER REFERENCES genres(id)
);

CREATE SEQUENCE movies_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER movies_trg
BEFORE INSERT ON movies
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT movies_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE actors (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100) NOT NULL
);

CREATE SEQUENCE actors_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER actors_trg
BEFORE INSERT ON actors
FOR EACH ROW
BEGIN
    IF :new.id IS NULL THEN
        SELECT actors_seq.NEXTVAL INTO :new.id FROM dual;
    END IF;
END;
/

CREATE TABLE movie_actors (
    movie_id NUMBER REFERENCES movies(id),
    actor_id NUMBER REFERENCES actors(id),
    PRIMARY KEY (movie_id, actor_id)
);

