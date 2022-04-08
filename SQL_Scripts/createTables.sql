DROP DATABASE IF EXISTS SalEats;
CREATE DATABASE SalEats;
USE SalEats;
DROP TABLE IF EXISTS Restaurant_details, rating_details, restaurant, category, users;



CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);


CREATE TABLE Restaurant_details (
  details_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  image_url VARCHAR(400) NOT NULL,
  address VARCHAR(300) NOT NULL,
  phone_no VARCHAR(200) NOT NULL,
  estimated_price VARCHAR(200),
  yelp_url VARCHAR(400) NOT NULL
);

CREATE TABLE Rating_details (
  rating_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  review_count INT NOT NULL,
  rating double NOT NULL
);

CREATE TABLE Restaurant (
  restaurant_id VARCHAR(300) PRIMARY KEY NOT NULL,
  restaurant_name VARCHAR(300) NOT NULL,
  details_id int,
  rating_id int,
  FOREIGN KEY (details_id) REFERENCES Restaurant_details(details_id),
  FOREIGN KEY (rating_id) REFERENCES Rating_details(rating_id)
);

CREATE TABLE Category (
  category_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  restaurant_id VARCHAR(300) NOT NULL
);


