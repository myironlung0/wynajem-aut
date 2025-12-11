CREATE TABLE `przeglad` (
  `id_przeglad` int NOT NULL AUTO_INCREMENT,
  `data_przegladu` date DEFAULT NULL,
  `samochod` int DEFAULT NULL,
  PRIMARY KEY (`id_przeglad`),
  INDEX `idx_samochodu_idx` (`samochod`),
  INDEX `idx_przeglad_data` (`data_przegladu`),
  CONSTRAINT `id_samochodu` FOREIGN KEY (`samochod`) REFERENCES `samochod` (`id_samochodu`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;