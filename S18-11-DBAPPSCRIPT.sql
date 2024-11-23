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
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM logistics;
DELETE FROM schedules;
DELETE FROM requests;
DELETE FROM vehicles;
DELETE FROM drivers;
DELETE FROM customers;

TRUNCATE TABLE logistics;
TRUNCATE TABLE schedules;
TRUNCATE TABLE requests;
TRUNCATE TABLE vehicles;
TRUNCATE TABLE drivers;
TRUNCATE TABLE customers;

INSERT INTO drivers (lastname, firstname, rate, contact_number, status) VALUES
('Smith', 'John', 13000.00, '09123456789', 'AVAILABLE'),
('Johnson', 'Emily', 15550.00, '09234567890', 'IN_TRANSIT'),
('Brown', 'Michael', 16750.00, '09345678901', 'ON_LEAVE'),
('Williams', 'Sophia', 18000.00, '09456789012', 'AVAILABLE'),
('Jones', 'James', 15200.00, '09567890123', 'AVAILABLE'),
('Garcia', 'Isabella', 16300.00, '09678901234', 'IN_TRANSIT'),
('Martinez', 'David', 15700.00, '09789012345', 'UNAVAILABLE'),
('Hernandez', 'Olivia', 15900.00, '09890123456', 'AVAILABLE'),
('Lopez', 'Liam', 15400.00, '09901234567', 'ON_LEAVE'),
('Gonzalez', 'Ava', 16100.00, '09012345678', 'AVAILABLE'),
('Taylor', 'Charlotte', 16200.00, '09111223344', 'IN_TRANSIT'),
('Anderson', 'Daniel', 16400.00, '09222334455', 'AVAILABLE'),
('Thomas', 'Amelia', 16500.00, '09333445566', 'AVAILABLE'),
('Jackson', 'Ethan', 16600.00, '09444556677', 'ON_LEAVE'),
('White', 'Mia', 15300.00, '09555667788', 'UNAVAILABLE'),
('Harris', 'Benjamin', 16800.00, '09666778899', 'IN_TRANSIT'),
('Martin', 'Lucas', 17000.00, '09777889900', 'AVAILABLE'),
('Clark', 'Ella', 15500.00, '09888990011', 'AVAILABLE'),
('Rodriguez', 'Lily', 17200.00, '09999001122', 'ON_LEAVE'),
('Lewis', 'Alexander', 16000.00, '09001122334', 'UNAVAILABLE');

INSERT INTO vehicles (plate_number, fuel_economy, last_maintenance_date, max_load_weight, status) VALUES
('ABC 123', 5.00, '2024-10-10', 3000, 'AVAILABLE'),
('DEF 456', 4.50, '2024-09-15', 3500, 'IN_TRANSIT'),
('GHI 789', 6.00, '2024-08-20', 4000, 'NEEDS_MAINTENANCE'),
('JKL 0123', 5.80, '2024-07-25', 4500, 'AVAILABLE'),
('MNO 234', 4.00, '2024-06-30', 2800, 'IN_TRANSIT'),
('PQR 5678', 5.20, '2024-05-10', 3200, 'UNAVAILABLE'),
('STU 890', 4.80, '2024-04-05', 5000, 'AVAILABLE'),
('VWX 3456', 6.50, '2024-03-18', 6000, 'NEEDS_MAINTENANCE'),
('YZA 678', 5.30, '2024-02-22', 3700, 'IN_TRANSIT'),
('BCD 9012', 5.60, '2024-01-12', 4200, 'AVAILABLE'),
('XYZ 482', 5.10, '2024-11-01', 3200, 'AVAILABLE'),
('QWE 9034', 4.80, '2024-10-05', 3800, 'IN_TRANSIT'),
('RTY 567', 5.40, '2024-09-15', 3000, 'AVAILABLE'),
('UIO 2345', 6.20, '2024-08-28', 4200, 'NEEDS_MAINTENANCE'),
('ASD 182', 4.60, '2024-07-12', 2700, 'AVAILABLE'),
('GHJ 4968', 5.00, '2024-06-25', 3500, 'IN_TRANSIT'),
('JKL 547', 5.90, '2024-05-10', 4500, 'UNAVAILABLE'),
('BNM 8321', 5.30, '2024-04-17', 5000, 'AVAILABLE'),
('VFR 9356', 6.00, '2024-03-30', 4000, 'IN_TRANSIT'),
('CDE 704', 4.70, '2024-02-22', 3900, 'NEEDS_MAINTENANCE');

INSERT INTO customers (company_name, customer_name, company_contact, billing_address, amount_paid, date_paid) VALUES
('Tech Solutions', 'John Doe', '09123456789', '123 Tech Street, Suite 101, City, Country', 840000.00, '2024-11-01'),
('Global Enterprises', 'Jane Smith', '09234567890', '456 Global Avenue, City, Country', 1288030.00, '2024-10-15'),
('Innovative Systems', 'Michael Brown', '09345678901', '789 Innovation Road, Suite 200, City, Country', 1008000.00, '2024-09-10'),
('Green Energy Corp', 'Sophia Lee', '09456789012', '101 Green Boulevard, City, Country', 123641.00, '2024-08-22'),
('Metro Logistics', 'David Williams', '09567890123', '202 Metro Park, City, Country', 95222.40, '2024-07-18'),
('Bright Future Ltd.', 'Olivia Johnson', '09678901234', '303 Bright Lane, Suite 15, City, Country', 112033.60, '2024-06-05'),
('Quantum Innovations', 'Benjamin White', '09789012345', '404 Quantum Drive, City, Country', 140044.80, '2024-05-10'),
('Oceanic Ventures', 'Mia Harris', '09890123456', '505 Oceanview Terrace, Suite 8, City, Country', 145653.60, '2024-04-03'),
('CloudTech Solutions', 'Lucas Martinez', '09901234567', '606 Cloud Street, City, Country', 117611.20, '2024-03-15'),
('Future Dynamics', 'Ava Robinson', '09012345678', '707 Future Crescent, City, Country', 134400.00, '2024-02-25'),
('Skyline Innovations', 'James Taylor', '09122334455', '123 Skyline Road, Suite 210, City, Country', 89600.00, '2024-11-10'),
('FutureTech Solutions', 'Charlotte Wilson', '09233445566', '234 Future Drive, City, Country', 106458.00, '2024-10-01'),
('Alpha Ventures', 'Alexander Martinez', '09344556677', '345 Alpha Lane, Suite 18, City, Country', 95242.00, '2024-09-05'),
('NextGen Technologies', 'Emma Clark', '09455667788', '456 NextGen Avenue, City, Country', 123313.80, '2024-08-14'),
('Premier Logistics', 'Liam Adams', '09566778899', '567 Premier Street, City, Country', 117381.40, '2024-07-25'),
('Innovative Solutions Inc.', 'Ethan Robinson', '09677889900', '678 Innovation Plaza, City, Country', 134433.60, '2024-06-17'),
('GreenTech Industries', 'Ava Martinez', '09788990011', '789 GreenTech Boulevard, Suite 7, City, Country', 128548.80, '2024-05-20'),
('Quantum Enterprises', 'Daniel Scott', '09899001122', '890 Quantum Park, Suite 21, City, Country', 140011.20, '2024-04-18'),
('Oceanic Resources', 'Sophia Wright', '09900112233', '901 Oceanic Road, City, Country', 100800.00, '2024-03-02'),
('Global Horizons', 'William Davis', '09011223344', '123 Global Lane, Suite 5, City, Country', 145628.00, '2024-02-12');


INSERT INTO requests (requested_date, product, origin, destination, load_weight, customer_id) VALUES
('2024-11-01', 'Electronics', 'New York', 'Los Angeles', 1200.50, 1),
('2024-10-15', 'Furniture', 'Chicago', 'Miami', 2500.00, 2),
('2024-09-05', 'Machinery', 'Houston', 'San Francisco', 4500.30, 3),
('2024-08-14', 'Clothing', 'Atlanta', 'Dallas', 2000.00, 4),
('2024-07-25', 'Food Supplies', 'Denver', 'Las Vegas', 1800.00, 5);

INSERT INTO schedules (date, driver_id, vehicle_id, request_id) VALUES
('2024-11-02 08:00:00', 1, 1, 1),
('2024-10-16 09:00:00', 2, 2, 2),
('2024-09-06 10:00:00', 3, 3, 3),
('2024-08-15 11:00:00', 4, 4, 4),
('2024-07-26 12:00:00', 5, 5, 5);
