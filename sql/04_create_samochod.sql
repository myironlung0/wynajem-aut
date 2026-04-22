CREATE TABLE `samochod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `marka` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `nr_VIN` varchar(17) NOT NULL,
  `przebieg` int NOT NULL,
  `cena` decimal(10,2) NOT NULL, -- 10 cyfr, 2 miejsca po przecinku
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nr_VIN_UNIQUE` (`nr_VIN`),
  INDEX `idx_samochod_cena` (`cena`), -- do filtrowania po cenie
  INDEX `idx_samochod_marka_model` (`marka`, `model`),
  INDEX `idx_samochod_przebieg` (`przebieg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE samochod ADD COLUMN zdjecie VARCHAR(500);