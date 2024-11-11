INSERT INTO driver (DriverID, LastName, FirstName, Rate, ContactNumber, LicenseRestrictions, Status, VehicleID)
VALUES
(1, 'Doe', 'John', 1.5, '555-1234', 'None', 'Available', NULL),
(2, 'Smith', 'Jane', 1.8, '555-5678', 'B', 'Available', NULL),
(3, 'Brown', 'Charlie', 2.0, '555-9012', 'C', 'Working', 1);

INSERT INTO vehicle (VehicleID, PlateNumber, Make, Model, Type, FuelEconomy, LastMaintenanceDate, MaxLoadWeight, Status, DriverID)
VALUES
(1, 'ABC123', 'Ford', 'F-150', 'Truck', 0.12, '2024-10-01 08:30:00', 1000.0, 'Available', NULL),
(2, 'XYZ789', 'Chevrolet', 'Silverado', 'Truck', 0.15, '2024-09-15 09:00:00', 1500.0, 'Unavailable', 2),
(3, 'LMN456', 'Ram', '1500', 'Truck', 0.10, '2024-11-05 10:45:00', 1200.0, 'Requires Maintenance', NULL);

INSERT INTO customer (CustomerID, CompanyName, CustomerName, CompanyContract, BillingAddress, AmountPaid, DatePaid, LogisticsID)
VALUES
(1, 'Acme Corp', 'Alice Johnson', 'Standard Contract', '123 Elm Street', 500.00, '2024-10-15 15:20:00', NULL),
(2, 'Beta Ltd', 'Bob Smith', 'Premium Contract', '456 Oak Avenue', 1200.00, '2024-10-18 14:10:00', NULL),
(3, 'Gamma Inc', 'Charlie Brown', 'Basic Contract', '789 Pine Road', 300.00, '2024-10-22 12:45:00', NULL);

INSERT INTO logistics (LogisticsID, Date, LoadType, LoadWeight, Distance, NormalCost, Status, DriverID, VehicleID, CustomerID)
VALUES
(1, '2024-11-01 09:00:00', 'Electronics', 800.0, 150.0, 180.00, 'Assigned', 1, 1, 1),
(2, '2024-11-02 10:30:00', 'Furniture', 1200.0, 200.0, 300.00, 'Completed', 2, 2, 2),
(3, '2024-11-03 11:45:00', 'Food', 600.0, 100.0, 120.00, 'Canceled', 3, 3, 3);
