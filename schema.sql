CREATE DATABASE ccinfom;

USE ccinfom;

CREATE TABLE Vehicle (
	VehicleID INT PRIMARY KEY,
    PlateNumber VARCHAR(6) NOT NULL,
    Type VARCHAR(20),
    FuelEconomy FLOAT NOT NULL,
    LastMaintenanceDate DATETIME NOT NULL,
    MaxLoadWeight FLOAT NOT NULL,
    Status ENUM('Available', 'Unavailable') NOT NULL,
    DriverID INT,
    FOREIGN KEY(DriverID) REFERENCES Driver(DriverID)
);

CREATE TABLE Driver (
    DriverID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(255) NOT NULL,
    Rate DECIMAL(10,2) NOT NULL,
    ContactNumber VARCHAR(15) NOT NULL,
    LicenseRestrictions ENUM('BE','CE') NOT NULL,
    Status ENUM('Available','Unavailable') NOT NULL,
    VehicleID INT,
    FOREIGN KEY (VehicleID) REFERENCES Vehicles(VehicleID)
);

CREATE TABLE Customer (
    CustomerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CompanyName VARCHAR(255) NOT NULL,
    CustomerName VARCHAR(255) NOT NULL,
    CompanyContract VARCHAR(255) NOT NULL,
    BillingAddress VARCHAR(255) NOT NULL,
    AmountPaid DECIMAL(10, 2) NOT NULL,
    DatePaid DATETIME NOT NULL,
    LogisticsID INT,
    FOREIGN KEY (LogisticsID) REFERENCES Logistics(LogisticsID)
);

CREATE TABLE Logistics (
    LogisticsID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Date DATETIME NOT NULL,
    LoadType VARCHAR(50) NOT NULL,
    LoadWeight DECIMAL(10, 2) NOT NULL,
    Distance DECIMAL(10, 2) NOT NULL,
    NormalCost DECIMAL(10, 2) NOT NULL,
    Status ENUM('Assigned', 'Completed', 'Cancelled') NOT NULL,
    DriverID INT,
    VehicleID INT,
    CustomerID INT,
    FOREIGN KEY (DriverID) REFERENCES Driver(DriverID),
    FOREIGN KEY (VehicleID) REFERENCES Vehicle(VehicleID),
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);
