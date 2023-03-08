CREATE TABLE person
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    birthday    DATE         NOT NULL
);