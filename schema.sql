SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS `ccinfom`;
USE `ccinfom`;

DROP TABLE IF EXISTS `drivers`;
CREATE TABLE drivers (
    driver_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(45) NOT NULL,
    firstname VARCHAR(45) NOT NULL,
    rate DECIMAL(10,2) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    status ENUM('AVAILABLE','IN_TRANSIT', 'ON_LEAVE', 'UNAVAILABLE') DEFAULT 'AVAILABLE'
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `vehicles`;
CREATE TABLE vehicles (
    vehicle_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    plate_number VARCHAR(8) UNIQUE NOT NULL,
    fuel_economy FLOAT NOT NULL,
    last_maintenance_date DATE,
    max_load_weight FLOAT NOT NULL,
    status ENUM('AVAILABLE', 'IN_TRANSIT', 'NEEDS_MAINTENANCE', 'UNAVAILABLE') DEFAULT 'AVAILABLE'
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `customers`;
CREATE TABLE customers (
    customer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    company_contact VARCHAR(255) NOT NULL,
    billing_address VARCHAR(255) NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    date_paid DATE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `requests`;
CREATE TABLE requests (
    request_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    requested_date DATE NOT NULL,
    product VARCHAR(50) NOT NULL,
    origin VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    load_weight DECIMAL(10, 2) NOT NULL,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `schedules`;
CREATE TABLE schedules (
    schedule_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    driver_id INT,
    vehicle_id INT,
    request_id INT,
    FOREIGN KEY (driver_id) REFERENCES drivers(driver_id) ON DELETE SET NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE SET NULL,
    FOREIGN KEY (request_id) REFERENCES requests(request_id) ON DELETE CASCADE
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `logistics`;
CREATE TABLE logistics (
    logistics_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    distance DECIMAL(10, 2) NOT NULL,
    normal_cost DECIMAL(10, 2) NOT NULL,
    status ENUM('ARRIVED', 'IN_TRANSIT', 'CANCELLED', 'PENDING') DEFAULT 'PENDING',
    schedule_id INT,
    FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id) ON DELETE SET NULL
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
