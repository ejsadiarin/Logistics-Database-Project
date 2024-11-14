CREATE DATABASE IF NOT EXISTS `ccinfom`;

USE `ccinfom`;

DROP TABLE IF EXISTS `driver`;
CREATE TABLE driver (
    driver_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    rate DECIMAL(10,2) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    status ENUM('AVAILABLE','IN TRANSIT', 'ON LEAVE', 'UNAVAILABLE') DEFAULT 'AVAILABLE'
);

DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE vehicle (
	vehicle_id INT PRIMARY KEY,
    plate_number VARCHAR(6) NOT NULL,
    fuel_economy FLOAT NOT NULL,
    last_maintenance_date DATETIME NOT NULL,
    max_load_weight FLOAT NOT NULL,
    status ENUM('AVAILABLE', 'IN TRANSIT', 'NEEDS MAINTENANCE', 'UNAVAILABLE') DEFAULT 'AVAILABLE'
);

DROP TABLE IF EXISTS `customer`;
CREATE TABLE customer (
    customer_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    company_contract VARCHAR(255) NOT NULL,
    billing_address VARCHAR(255) NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    date_paid DATETIME NOT NULL
);

DROP TABLE IF EXISTS `request`;
CREATE TABLE request (
    request_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    requested_date DATETIME NOT NULL,
    product VARCHAR(50) NOT NULL,
    origin DECIMAL(10, 2) NOT NULL,
    destination DECIMAL(10, 2) NOT NULL,
    load_weight DECIMAL(10, 2) NOT NULL,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

DROP TABLE IF EXISTS `schedule`;
CREATE TABLE schedule (
    schedule_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    driver_id INT,
    vehicle_id INT,
    request_id INT,
    FOREIGN KEY (driver_id) REFERENCES driver(driver_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id),
    FOREIGN KEY (request_id) REFERENCES request(request_id)
);

DROP TABLE IF EXISTS `logistics`;
CREATE TABLE logistics (
    logisticsID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    distance DECIMAL(10, 2) NOT NULL,
    normalCost DECIMAL(10, 2) NOT NULL,
    status ENUM('ARRIVED', 'IN TRANSIT', 'CANCELLED', 'PENDING') DEFAULT 'PENDING',
    schedule_id INT,
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id)
);
