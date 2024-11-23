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
('Smith', 'John', 50.00, '09123456789', 'AVAILABLE'),
('Johnson', 'Emily', 55.00, '09234567890', 'IN_TRANSIT'),
('Brown', 'Michael', 60.00, '09345678901', 'ON_LEAVE'),
('Williams', 'Sophia', 58.00, '09456789012', 'AVAILABLE'),
('Jones', 'James', 52.00, '09567890123', 'AVAILABLE'),
('Garcia', 'Isabella', 63.00, '09678901234', 'IN_TRANSIT'),
('Martinez', 'David', 57.00, '09789012345', 'UNAVAILABLE'),
('Hernandez', 'Olivia', 59.00, '09890123456', 'AVAILABLE'),
('Lopez', 'Liam', 54.00, '09901234567', 'ON_LEAVE'),
('Gonzalez', 'Ava', 61.00, '09012345678', 'AVAILABLE');

INSERT INTO drivers (lastname, firstname, rate, contact_number, status) VALUES
('Taylor', 'Charlotte', 62.00, '09111223344', 'IN_TRANSIT'),
('Anderson', 'Daniel', 64.00, '09222334455', 'AVAILABLE'),
('Thomas', 'Amelia', 65.00, '09333445566', 'AVAILABLE'),
('Jackson', 'Ethan', 66.00, '09444556677', 'ON_LEAVE'),
('White', 'Mia', 53.00, '09555667788', 'UNAVAILABLE'),
('Harris', 'Benjamin', 68.00, '09666778899', 'IN_TRANSIT'),
('Martin', 'Lucas', 70.00, '09777889900', 'AVAILABLE'),
('Clark', 'Ella', 55.00, '09888990011', 'AVAILABLE'),
('Rodriguez', 'Lily', 72.00, '09999001122', 'ON_LEAVE'),
('Lewis', 'Alexander', 60.00, '09001122334', 'UNAVAILABLE');

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
('BCD 9012', 5.60, '2024-01-12', 4200, 'AVAILABLE');

INSERT INTO vehicles (plate_number, fuel_economy, last_maintenance_date, max_load_weight, status) VALUES
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
('Tech Solutions', 'John Doe', '09123456789', '123 Tech Street, Suite 101, City, Country', 1500.00, '2024-11-01'),
('Global Enterprises', 'Jane Smith', '09234567890', '456 Global Avenue, City, Country', 2300.50, '2024-10-15'),
('Innovative Systems', 'Michael Brown', '09345678901', '789 Innovation Road, Suite 200, City, Country', 1800.00, '2024-09-10'),
('Green Energy Corp', 'Sophia Lee', '09456789012', '101 Green Boulevard, City, Country', 2200.75, '2024-08-22'),
('Metro Logistics', 'David Williams', '09567890123', '202 Metro Park, City, Country', 1700.40, '2024-07-18'),
('Bright Future Ltd.', 'Olivia Johnson', '09678901234', '303 Bright Lane, Suite 15, City, Country', 2000.60, '2024-06-05'),
('Quantum Innovations', 'Benjamin White', '09789012345', '404 Quantum Drive, City, Country', 2500.80, '2024-05-10'),
('Oceanic Ventures', 'Mia Harris', '09890123456', '505 Oceanview Terrace, Suite 8, City, Country', 2600.90, '2024-04-03'),
('CloudTech Solutions', 'Lucas Martinez', '09901234567', '606 Cloud Street, City, Country', 2100.20, '2024-03-15'),
('Future Dynamics', 'Ava Robinson', '09012345678', '707 Future Crescent, City, Country', 2400.00, '2024-02-25');

INSERT INTO customers (company_name, customer_name, company_contact, billing_address, amount_paid, date_paid) VALUES
('Skyline Innovations', 'James Taylor', '09122334455', '123 Skyline Road, Suite 210, City, Country', 1600.00, '2024-11-10'),
('FutureTech Solutions', 'Charlotte Wilson', '09233445566', '234 Future Drive, City, Country', 1900.50, '2024-10-01'),
('Alpha Ventures', 'Alexander Martinez', '09344556677', '345 Alpha Lane, Suite 18, City, Country', 1700.75, '2024-09-05'),
('NextGen Technologies', 'Emma Clark', '09455667788', '456 NextGen Avenue, City, Country', 2200.25, '2024-08-14'),
('Premier Logistics', 'Liam Adams', '09566778899', '567 Premier Street, City, Country', 2100.40, '2024-07-25'),
('Innovative Solutions Inc.', 'Ethan Robinson', '09677889900', '678 Innovation Plaza, City, Country', 2400.60, '2024-06-17'),
('GreenTech Industries', 'Ava Martinez', '09788990011', '789 GreenTech Boulevard, Suite 7, City, Country', 2300.80, '2024-05-20'),
('Quantum Enterprises', 'Daniel Scott', '09899001122', '890 Quantum Park, Suite 21, City, Country', 2500.20, '2024-04-18'),
('Oceanic Resources', 'Sophia Wright', '09900112233', '901 Oceanic Road, City, Country', 1800.00, '2024-03-02'),
('Global Horizons', 'William Davis', '09011223344', '123 Global Lane, Suite 5, City, Country', 2600.50, '2024-02-12');

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

-- Insert records into the logistics table with normal_cost based on formula
INSERT INTO logistics (distance, normal_cost, status, schedule_id) VALUES
(300, (5.00 * 300) + (5.00 * 300), 'IN_TRANSIT', 1),  -- Vehicle 1, Driver 1
(450, (4.80 * 450) + (6.50 * 450), 'ARRIVED', 2),     -- Vehicle 2, Driver 2
(600, (5.40 * 600) + (4.75 * 600), 'PENDING', 3),     -- Vehicle 3, Driver 3
(200, (6.20 * 200) + (5.25 * 200), 'IN_TRANSIT', 4),  -- Vehicle 4, Driver 4
(350, (5.10 * 350) + (6.00 * 350), 'CANCELLED', 5);   -- Vehicle 5, Driver 5