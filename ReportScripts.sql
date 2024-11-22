
-- Sales Report by MONTH
SELECT 
    DATE(date_paid) AS SalesDate,
    SUM(amount_paid) AS TotalSales,
    AVG(amount_paid) AS AverageSales
FROM 
    customer
WHERE 
    YEAR(date_paid) = ? AND MONTH(date_paid) = ?
GROUP BY 
    DATE(date_paid)
ORDER BY 
    sales_date;

-- Sales Report by YEAR
SELECT 
    DATE(date_paid) AS SalesDate,
    SUM(amount_paid) AS TotalSales,
    AVG(amount_paid) AS AverageSales
FROM
    customer
WHERE 
    YEAR(date_paid) = ?
GROUP BY
    DATE(date_paid)
ORDER BY
    sales_date;

-- Driver Completed Trips Report
SELECT 
    d.driver_id,
    CONCAT(d.lastname, d.firstname) AS DriverName,
    MONTH(s.date) AS Month,
    COUNT(l.logistics_id) AS TotalTrips,
    SUM(l.distance) AS TotalKilometers
FROM 
    logistics l
JOIN 
    schedule s ON l.schedule_id = s.schedule_id
JOIN 
    driver d ON s.driver_id = d.driver_id
WHERE 
    YEAR(s.date) = ?
GROUP BY 
    d.driver_id, MONTH(s.date)
ORDER BY 
    d.driver_id, Month;

-- Vehicle Completed Trips Report
SELECT 
    v.vehicle_id,
    CONCAT(v.plate_number, ' (', v.vehicle_id, ')') AS VehicleName,
    MONTH(s.date) AS Month,
    COUNT(l.logistics_id) AS TotalTrips,
    SUM(l.distance) AS TotalKilometers
FROM 
    logistics l
JOIN 
    schedules s ON l.schedule_id = s.schedule_id
JOIN 
    vehicles v ON s.vehicle_id = v.vehicle_id
WHERE 
    YEAR(schedule.date) = ?
GROUP BY 
    v.vehicle_id, MONTH(s.date)
ORDER BY 
    v.vehicle_id, Month;

