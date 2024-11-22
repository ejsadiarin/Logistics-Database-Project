


-- Trigger to check if the load weight exceeds the vehicle capacity
CREATE TRIGGER check_load_capacity
BEFORE INSERT ON Schedule
FOR EACH ROW
BEGIN
    DECLARE max_capacity DECIMAL(10, 2);
    SELECT MaxLoadWeight INTO max_capacity 
    FROM Vehicle 
    WHERE VehicleID = NEW.VehicleID;
    
    IF NEW.LoadWeight > max_capacity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Load weight exceeds vehicle capacity';
    END IF;
END;

-- Trigger to update the logistics status to "In Transit" when the scheduled time is past
CREATE TRIGGER update_in_transit_status
AFTER UPDATE ON Schedule
FOR EACH ROW
BEGIN
    -- Check if the current time is past the scheduled time and logistics status is "Pending"
    IF NEW.Date <= NOW() THEN
        -- Update the status of the logistics order to "In Transit"
        UPDATE Logistics
        SET Status = 'IN_TRANSIT'
        WHERE ScheduleID = NEW.ScheduleID AND Status = 'PENDING';
        
        -- Update the driver status to "In Transit"
        UPDATE Driver
        SET Status = 'IN_TRANSIT'
        WHERE DriverID = NEW.DriverID;

        -- Update the vehicle status to "In Transit"
        UPDATE Vehicle
        SET Status = 'IN_TRANSIT'
        WHERE VehicleID = NEW.VehicleID;
    END IF;
END;


-- Trigger to update a vehicles status to "In_maintenance" when the last maintenance date was 6 months ago
CREATE TRIGGER check_vehicle_maintenance_date
AFTER UPDATE ON Logistics
FOR EACH ROW
BEGIN
    -- Check if the logistics order was marked as "Completed"
    IF NEW.Status = 'Completed' THEN
        DECLARE last_maintenance_date DATE;
        
        -- Retrieve the latest maintenance date for the assigned vehicle
        SELECT LastMaintenanceDate
        INTO last_maintenance_date
        FROM Vehicle
        WHERE VehicleID = (SELECT VehicleID FROM Schedule WHERE ScheduleID = NEW.ScheduleID);
        
        -- Check if the last maintenance was more than 6 months ago
        IF last_maintenance_date <= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) THEN
            -- Update the vehicle's status to "In Maintenance"
            UPDATE Vehicle
            SET Status = 'In Maintenance'
            WHERE VehicleID = (SELECT VehicleID FROM Schedule WHERE ScheduleID = NEW.ScheduleID);
        END IF;
    END IF;
END;

