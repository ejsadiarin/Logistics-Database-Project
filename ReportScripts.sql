
-- Sales Report by MONTH
SELECT 
    DATE(DatePaid) AS SalesDate,
    SUM(AmountPaid) AS TotalSales,
    AVG(AmountPaid) AS AverageSales
FROM 
    Customer
WHERE 
    YEAR(DatePaid) = ? AND MONTH(DatePaid) = ?
GROUP BY 
    DATE(DatePaid)
ORDER BY 
    SalesDate;

-- Sales Report by YEAR
SELECT 
    DATE(DatePaid) AS SalesDate,
    SUM(AmountPaid) AS TotalSales,
    AVG(AmountPaid) AS AverageSales
FROM
    Customer
WHERE 
    YEAR(DatePaid) = ?
GROUP BY
    DATE(DatePaid)
ORDER BY
    SalesDate;

-- Driver Completed Trips Report
SELECT 
    d.DriverID,
    CONCAT(d.FullName, ' (', d.DriverID, ')') AS DriverName,
    MONTH(s.Date) AS Month,
    COUNT(l.LogisticsID) AS TotalTrips,
    SUM(l.Distance) AS TotalKilometers
FROM 
    Logistics l
JOIN 
    Schedule s ON l.ScheduleID = s.ScheduleID
JOIN 
    Driver d ON s.DriverID = d.DriverID
WHERE 
    YEAR(s.Date) = ?
GROUP BY 
    d.DriverID, MONTH(s.Date)
ORDER BY 
    d.DriverID, Month;

-- Vehicle Completed Trips Report
SELECT 
    v.VehicleID,
    CONCAT(v.PlateNumber, ' (', v.VehicleID, ')') AS VehicleName,
    MONTH(s.Date) AS Month,
    COUNT(l.LogisticsID) AS TotalTrips,
    SUM(l.Distance) AS TotalKilometers
FROM 
    Logistics l
JOIN 
    Schedule s ON l.ScheduleID = s.ScheduleID
JOIN 
    Vehicle v ON s.VehicleID = v.VehicleID
WHERE 
    YEAR(Schedule.Date) = ?
GROUP BY 
    v.VehicleID, MONTH(s.Date)
ORDER BY 
    v.VehicleID, Month;

