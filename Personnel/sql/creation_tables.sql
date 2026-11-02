CREATE DATABASE IF NOT EXISTS personnel CHARACTER SET utf8mb4;
USE personnel;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS EMPLOYE;
DROP TABLE IF EXISTS LIGUE;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE LIGUE (
    Id_Ligue    INT AUTO_INCREMENT PRIMARY KEY,
    Nom_Ligue   VARCHAR(100) NOT NULL UNIQUE,
    Id_Admin    INT NOT NULL
) ENGINE = InnoDB;


CREATE TABLE EMPLOYE (
    Id_Employe            INT AUTO_INCREMENT PRIMARY KEY,
    Nom_Employe           VARCHAR(100) NOT NULL,
    Prenom_Employe        VARCHAR(100) NOT NULL,
    Mail_Employe          VARCHAR(255) NOT NULL UNIQUE,
    Mdp_Employe           VARCHAR(255) NOT NULL,       -- à stocker haché, jamais en clair
    Date_Arrivee_Employe  DATE,
    Date_Depart_Employe   DATE,
    Super_Admin           BOOLEAN NOT NULL DEFAULT FALSE,
    Id_Ligue              INT NULL,
    CONSTRAINT fk_employe_ligue FOREIGN KEY (Id_Ligue) REFERENCES LIGUE (Id_Ligue),
    CONSTRAINT chk_date_depart CHECK (
        Date_Depart_Employe IS NULL
        OR Date_Arrivee_Employe IS NULL
        OR Date_Depart_Employe >= Date_Arrivee_Employe
    ),
    CONSTRAINT chk_date_arrivee CHECK (
        Super_Admin = TRUE
        OR Date_Arrivee_Employe IS NOT NULL
    )
) ENGINE = InnoDB;

ALTER TABLE LIGUE
    ADD CONSTRAINT fk_ligue_admin FOREIGN KEY (Id_Admin) REFERENCES EMPLOYE (Id_Employe);