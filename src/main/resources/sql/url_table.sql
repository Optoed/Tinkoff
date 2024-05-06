CREATE TABLE urls (
                      id BIGSERIAL PRIMARY KEY,
                      longURL varchar(255),
                      shortURL VARCHAR(255),
                      CONSTRAINT unique_age_color UNIQUE (longURL, shortURL)
);

CREATE SEQUENCE url_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO CYCLE;