DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS
(
    ID   BIGINT IDENTITY PRIMARY KEY,
    NAME VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES
(
    ID   BIGINT IDENTITY PRIMARY KEY,
    GENRE VARCHAR(255) UNIQUE NOT NULL
);

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS
(
    ID        BIGINT IDENTITY PRIMARY KEY,
    TITLE     VARCHAR(255) NOT NULL,
    AUTHOR_ID BIGINT REFERENCES AUTHORS(ID),
    GENRE_ID  BIGINT REFERENCES GENRES(ID)
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    ID BIGINT IDENTITY PRIMARY KEY,
    BOOK_ID BIGINT REFERENCES BOOKS(ID) ON DELETE CASCADE,
    COMMENT CLOB NOT NULL
);

-- DROP TABLE IF EXISTS BOOK_AUTHOR;
-- CREATE TABLE BOOK_AUTHOR
-- (
--     BOOK_ID   BIGINT NOT NULL REFERENCES BOOKS(ID) ON DELETE CASCADE,
--     AUTHOR_ID BIGINT NOT NULL REFERENCES AUTHORS(ID),
--     PRIMARY KEY (BOOK_ID, AUTHOR_ID)
-- );