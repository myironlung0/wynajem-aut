CREATE TABLE `platnosc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_uzytkownika` int NOT NULL,
  `czy_zrealizowano` enum('T','N') DEFAULT NULL,
  `data_platnosci` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_id_uzytkownika` (`id_uzytkownika`),
  CONSTRAINT `id_uzytkownika` FOREIGN KEY (`id_uzytkownika`) REFERENCES uzytkownik(`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
