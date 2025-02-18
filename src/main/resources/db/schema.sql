CREATE DATABASE IF NOT EXISTS Contact_Application;
USE Contact_Application;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS contacts (
    contact_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    phone_number INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50),
    PRIMARY KEY (contact_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);



