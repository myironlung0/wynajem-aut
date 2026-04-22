CREATE TABLE rezerwacja (
    `id` INT AUTO_INCREMENT,
    `data_od` date NOT NULL,
    `data_do` date NOT NULL,
    `nr_tel` int NOT NULL,
    `email` varchar(100) NOT NULL,
    `id_uzytkownika` int NOT NULL,
    `id_samochodu` int NOT NULL,
    `nr_rezerwacji` varchar(20) UNIQUE NOT NULL,
    `status` enum('potwierdzona', 'anulowana', 'w_trakcie', 'zakonczona') DEFAULT 'potwierdzona',
    PRIMARY KEY(`id`),
    FOREIGN KEY(`id_uzytkownika`) REFERENCES uzytkownik(id) ON DELETE RESTRICT,
    FOREIGN KEY(`id_samochodu`) REFERENCES samochod(`id`),
    INDEX `idx_rezerwacja_status` (`status`),
    INDEX `idx_rezerwacja_numer` (`nr_rezerwacji`),
    INDEX `idx_rezerwacja_daty` (`data_od`, `data_do`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE rezerwacja
    MODIFY data_od DATETIME NOT NULL,
    MODIFY data_do DATETIME NOT NULL,
    ADD COLUMN cena_koncowa DECIMAL(10,2) NOT NULL AFTER data_do;

