


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
