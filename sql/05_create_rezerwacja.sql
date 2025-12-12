CREATE TABLE rezerwacja (
    `id_rezerwacja` INT AUTO_INCREMENT,
    `data_od` date NOT NULL,
    `data_do` date NOT NULL,
    `nr_tel` int DEFAULT NULL,
    `email` varchar(100) NOT NULL,
    `id_uzytkownika` int NOT NULL,
    `id_samochodu` int NOT NULL,
    `nr_rezerwacji` varchar(20) UNIQUE NOT NULL,
    `status` enum('potwierdzona', 'anulowana', 'w_trakcie', 'zakonczona') DEFAULT 'potwierdzona',
    PRIMARY KEY(`id_rezerwacja`),
    FOREIGN KEY(`id_uzytkownika`) REFERENCES uzytkownik(id),
    FOREIGN KEY(`id_samochodu`) REFERENCES samochod(`id_samochodu`),
    INDEX `idx_rezerwacja_status` (`status`),
    INDEX `idx_rezerwacja_numer` (`nr_rezerwacji`),
    INDEX `idx_rezerwacja_daty` (`data_od`, `data_do`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;