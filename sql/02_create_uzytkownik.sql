CREATE TABLE `uzytkownik` (
  `id` int NOT NULL AUTO_INCREMENT,
  `imie` varchar(50) NOT NULL,
  `nazwisko` varchar(50) NOT NULL,
  `adres` varchar(100) DEFAULT NULL,
  `miejscowosc` varchar(50) DEFAULT NULL,
  `nr_tel` int(9) NOT NULL UNIQUE,
  `email` varchar(100) NOT NULL UNIQUE,
  `nr_dowodu` varchar(9) DEFAULT NULL,
  `data_ur` date DEFAULT NULL,
  czy_zweryfikowany ENUM('T','N') DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email`),
  UNIQUE INDEX `nr_dowodu_UNIQUE` (`nr_dowodu`),
  INDEX `idx_uzytkownik_nazwisko` (`nazwisko`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;