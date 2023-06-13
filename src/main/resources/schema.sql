DROP DATABASE IF EXISTS chattop;
CREATE DATABASE chattop;
USE chattop;

DROP TABLE IF EXISTS `USERS`;
DROP TABLE IF EXISTS `RENTALS`;
DROP TABLE IF EXISTS `MESSAGES`;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255),
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `RENTALS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` numeric,
  `price` numeric,
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` integer NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `rentals_ibfk1` FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE `MESSAGES` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `rental_id` integer,
  `user_id` integer,
  `message` varchar(2000),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `messages_ibfk1` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `messages_ibfk2` FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);

INSERT INTO USERS VALUES (1,'testeur1@test.com','testeur1','$2y$10$6HruTu/bV96MlIet7HrFAelYQnaW9sGRO69f3yt63vk5sXljrhIhu',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'testeur2@test.com','testeur1','$2y$10$6HruTu/bV96MlIet7HrFAelYQnaW9sGRO69f3yt63vk5sXljrhIhu',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
UNLOCK TABLES;
 
LOCK TABLES RENTALS WRITE;
INSERT INTO RENTALS VALUES (1,'maison de rêve',150,250,'https://lh3.googleusercontent.com/p/AF1QipNWj_fIU8cS5vlRNTtMB2LuIGQIx7rmrTaMbvhu=s1360-w1360-h1020','trop belle!',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP),
(2,'cap Espagne',150,250,'https://cdn.lecollectionist.com/lc/production/uploads/photos/house-4989/2021-10-07-922ceced891535c3c04c414253c487e6.jpg?q=65&w=1800','Rosalicia est une luxueuse villa avec piscine privée, située dans une rue calme à 800 mètres de la plage. Convient aux familles.!',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
UNLOCK TABLES;

LOCK TABLES MESSAGES WRITE;
INSERT INTO MESSAGES VALUES (1,1,2,'TU ME FAIS UNE RISTOURNE',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
UNLOCK TABLES;
