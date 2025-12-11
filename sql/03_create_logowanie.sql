CREATE TABLE logowanie (
    `id` int NOT NULL AUTO_INCREMENT,
    `id_uzytkownika` int UNIQUE NOT NULL,
    `nazwa_uzytkownika` VARCHAR(50) UNIQUE NOT NULL,
    `haslo_hash` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_uzytkownika`) REFERENCES uzytkownik(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;