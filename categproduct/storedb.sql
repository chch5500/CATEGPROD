CREATE USER 'storeusr'@'localhost' IDENTIFIED BY 'storepsw';
CREATE DATABASE storedb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
GRANT SELECT, INSERT, UPDATE, DELETE ON storedb.* TO 'storeusr'@'localhost';
USE storedb;
CREATE TABLE `categories` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `products` (
    `id` INT(4) NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL UNIQUE,
    `name` VARCHAR(20) NOT NULL,
    `stock` INT DEFAULT 0,
    `price` DOUBLE DEFAULT 0.0,
    `category_id` INT(4),
    PRIMARY KEY (`id`)
);
ALTER TABLE `products` 
    ADD CONSTRAINT `fk_category` FOREIGN KEY (category_id) 
    REFERENCES categories(id)
    ON UPDATE CASCADE ON DELETE RESTRICT;
INSERT INTO categories VALUES 
    (1, "C01", "category01"),
    (2, "C02", "category02"),
    (3, "C03", "category03"),
    (4, "C04", "category04"),
    (5, "C05", "category05"),
    (6, "C06", "category06");
INSERT INTO products VALUES 
    (1, "P01", "product01", 101, 1001.0, 1),
    (2, "P02", "product02", 102, 1002.0, 2),
    (3, "P03", "product03", 103, 1003.0, 3),
    (4, "P04", "product04", 104, 1004.0, 4),
    (5, "P05", "product05", 105, 1005.0, 5),
    (6, "P06", "product06", 106, 1006.0, 1),
    (7, "P07", "product07", 107, 1007.0, 1),
    (8, "P08", "product08", 108, 1008.0, 2),
    (9, "P09", "product09", 109, 1009.0, 3);
