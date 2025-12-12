CREATE TABLE `przeglad` (
  `id_przeglad` int NOT NULL AUTO_INCREMENT,
  `data_przegladu` date DEFAULT NULL,
  `id_samochodu` int NOT NULL,
  PRIMARY KEY (`id_przeglad`),
  INDEX `idx_samochodu_id` (`id_samochodu`),
  INDEX `idx_przeglad_data` (`data_przegladu`),
  CONSTRAINT `id_samochodu` FOREIGN KEY (`id_samochodu`) REFERENCES `samochod` (`id_samochodu`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;