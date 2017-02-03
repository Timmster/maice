DROP SCHEMA IF EXISTS MAICE;
CREATE SCHEMA MAICE;

DROP TABLE IF EXISTS MAICE.USER;
CREATE TABLE MAICE.USER (
    ID bigint(20) NOT NULL AUTO_INCREMENT,
    USERNAME varchar(200) NOT NULL,
    PASSWORDHASH varchar(200) NOT NULL,
    ACTIVATIONHASH varchar(200),
    PRIMARY KEY (ID)
);
