CREATE TABLE ubezpieczenie (
    `id_ubezpieczenia` int AUTO_INCREMENT,
    `data_ubezpieczenia` date NOT NULL,
    `koszt` decimal(10,2) NOT NULL,
    `id_samochodu` int NOT NULL,
    PRIMARY KEY(`id_ubezpieczenia`),
    INDEX `idx_ubezpieczenie_data` (`data_ubezpieczenia`),
    INDEX `idx_ubezpieczenie_koszt` (`koszt`),  
    FOREIGN KEY (`id_samochodu`) REFERENCES samochod(`id_samochodu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;