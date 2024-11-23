-- Trigger to check if the load weight exceeds the vehicle capacity
CREATE TRIGGER check_load_capacity
BEFORE INSERT ON schedules
FOR EACH ROW
BEGIN
    DECLARE max_capacity DECIMAL(10, 2);
    SELECT max_load_capacity INTO max_capacity 
    FROM vehicles 
    WHERE vehicle_id = NEW.vehicle_id;
    
    IF NEW.load_weight > max_capacity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Load weight exceeds vehicle capacity';
    END IF;
END;

-- Trigger to update the logistics status to "IN_TRANSIT" when the scheduled time is past
CREATE TRIGGER update_in_transit_status
AFTER UPDATE ON schedules
FOR EACH ROW
BEGIN
    -- Check if the current time is past the scheduled time and logistics status is "Pending"
    IF NEW.date <= NOW() THEN
        -- Update the status of the logistics order to "IN_TRANSIT"
        UPDATE logistics
        SET status = 'IN_TRANSIT'
        WHERE schedule_id = NEW.schedule_id AND status = 'PENDING';
        
        -- Update the driver status to "IN_TRANSIT"
        UPDATE drivers
        SET status = 'IN_TRANSIT'
        WHERE driver_id = NEW.driver_id;

        -- Update the vehicle status to "IN_TRANSIT"
        UPDATE vehicles
        SET status = 'IN_TRANSIT'
        WHERE vehicle_id = NEW.vehicle_id;
    END IF;
END;


-- Trigger to update a vehicles status to "NEEDS_MAINTENANCE" when the last maintenance date was 6 months ago
CREATE TRIGGER check_vehicle_maintenance_date
AFTER UPDATE ON logistics
FOR EACH ROW
BEGIN
    -- Check if the logistics order was marked as "ARRIVED"
    IF NEW.status = 'ARRIVED' THEN
        DECLARE last_maintenance_date DATE;
        
        -- Retrieve the latest maintenance date for the assigned vehicle
        SELECT last_maintenance_date
        INTO last_maintenance_date
        FROM vehicles
        WHERE vehicle_id = (SELECT vehicle_id FROM schedule WHERE schedule_id = NEW.schedule_id);
        
        -- Check if the last maintenance was more than 6 months ago
        IF last_maintenance_date <= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) THEN
            -- Update the vehicle's status to "NEEDS_MAINTENANCE"
            UPDATE vehicles
            SET status = 'NEEDS_MAINTENANCE'
            WHERE vehicle_id = (SELECT vehicle_id FROM schedules WHERE schedule_id = NEW.schedule_id);
        END IF;
    END IF;
END;
