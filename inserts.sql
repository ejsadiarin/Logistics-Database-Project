INSERT INTO drivers (full_name, rate, contact_number, status) VALUES
('John Doe', 150.00, '1234567890', 'AVAILABLE'),
('Jane Smith', 200.00, '0987654321', 'IN_TRANSIT'),
('Tom Brown', 175.00, '1112223333', 'ON_LEAVE'),
('Emily Davis', 160.00, '4445556666', 'AVAILABLE'),
('Michael Johnson', 185.00, '7778889999', 'UNAVAILABLE'),
('Chris Lee', 190.00, '3334445555', 'AVAILABLE'),
('Sarah Wilson', 170.00, '6667778888', 'IN_TRANSIT'),
('Daniel Taylor', 155.00, '8889990000', 'AVAILABLE'),
('Sophia Harris', 180.00, '1113335555', 'ON_LEAVE'),
('Ryan Martinez', 165.00, '2224446666', 'AVAILABLE');

INSERT INTO vehicles (vehicle_id, plate_number, fuel_economy, last_maintenance_date, max_load_weight, status) VALUES
(1, 'ABC123', 12.5, '2024-01-15', 1000.0, 'AVAILABLE'),
(2, 'DEF456', 10.0, '2023-12-10', 1500.0, 'IN_TRANSIT'),
(3, 'GHI789', 15.0, '2024-02-01', 1200.0, 'AVAILABLE'),
(4, 'JKL012', 8.5, '2023-11-25', 2000.0, 'NEEDS_MAINTENANCE'),
(5, 'MNO345', 9.0, '2023-10-30', 1800.0, 'AVAILABLE'),
(6, 'PQR678', 11.0, '2023-09-15', 1600.0, 'UNAVAILABLE'),
(7, 'STU901', 14.5, '2024-01-05', 1300.0, 'AVAILABLE'),
(8, 'VWX234', 10.5, '2024-01-20', 1400.0, 'IN_TRANSIT'),
(9, 'YZA567', 13.0, '2023-08-15', 1700.0, 'AVAILABLE'),
(10, 'BCD890', 12.0, '2024-01-10', 1500.0, 'AVAILABLE');

INSERT INTO customers (company_name, customer_name, company_contact, billing_address, amount_paid, date_paid) VALUES
('Tech Corp', 'Alice Green', 'alice@techcorp.com', '123 Tech Lane, City', 500.00, '2024-01-01'),
('Logistics Inc', 'Bob White', 'bob@logisticsinc.com', '456 Logistics Rd, City', 1000.00, '2024-01-15'),
('Retail Solutions', 'Charlie Black', 'charlie@retailsolutions.com', '789 Retail St, City', 750.00, '2024-01-10'),
('Transport LLC', 'Diana Gray', 'diana@transportllc.com', '123 Transport Ave, City', 1200.00, '2023-12-20'),
('Global Traders', 'Evan Blue', 'evan@globaltraders.com', '456 Global Dr, City', 600.00, '2023-12-25'),
('Fresh Foods', 'Fiona Pink', 'fiona@freshfoods.com', '789 Fresh Blvd, City', 950.00, '2023-12-30'),
('Build It', 'George Gold', 'george@buildit.com', '123 Build St, City', 700.00, '2024-01-05'),
('Smart Tech', 'Hannah Silver', 'hannah@smarttech.com', '456 Smart Rd, City', 850.00, '2024-01-12'),
('Fast Delivery', 'Ian Copper', 'ian@fastdelivery.com', '789 Fast Ln, City', 550.00, '2023-12-28'),
('Green Supplies', 'Julia Violet', 'julia@greensupplies.com', '123 Green Way, City', 400.00, '2024-01-08');

INSERT INTO requests (requested_date, product, origin, destination, load_weight, customer_id) VALUES
('2024-01-01', 'Laptops', 'City A', 'City B', 200.00, 1),
('2024-01-02', 'Furniture', 'City C', 'City D', 500.00, 2),
('2024-01-03', 'Groceries', 'City E', 'City F', 300.00, 3),
('2024-01-04', 'Machinery', 'City G', 'City H', 700.00, 4),
('2024-01-05', 'Clothing', 'City I', 'City J', 150.00, 5),
('2024-01-06', 'Books', 'City K', 'City L', 250.00, 6),
('2024-01-07', 'Electronics', 'City M', 'City N', 400.00, 7),
('2024-01-08', 'Medical Supplies', 'City O', 'City P', 350.00, 8),
('2024-01-09', 'Toys', 'City Q', 'City R', 220.00, 9),
('2024-01-10', 'Appliances', 'City S', 'City T', 550.00, 10);

INSERT INTO schedules (date, driver_id, vehicle_id, request_id) VALUES
('2024-01-01 08:00:00', 1, 1, 1),
('2024-01-02 09:00:00', 2, 2, 2),
('2024-01-03 10:00:00', 3, 3, 3),
('2024-01-04 11:00:00', 4, 4, 4),
('2024-01-05 12:00:00', 5, 5, 5),
('2024-01-06 13:00:00', 6, 6, 6),
('2024-01-07 14:00:00', 7, 7, 7),
('2024-01-08 15:00:00', 8, 8, 8),
('2024-01-09 16:00:00', 9, 9, 9),
('2024-01-10 17:00:00', 10, 10, 10);

INSERT INTO logistics (distance, normal_cost, status, schedule_id) VALUES
(100.50, 200.00, 'ARRIVED', 1),
(150.75, 300.00, 'IN_TRANSIT', 2),
(200.25, 400.00, 'CANCELLED', 3),
(250.00, 500.00, 'PENDING', 4),
(180.30, 350.00, 'ARRIVED', 5),
(220.40, 450.00, 'IN_TRANSIT', 6),
(300.00, 600.00, 'CANCELLED', 7),
(120.50, 240.00, 'ARRIVED', 8),
(175.00, 350.00, 'PENDING', 9),
(90.00, 180.00, 'ARRIVED', 10);
