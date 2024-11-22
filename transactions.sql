USE `ccinfom`;

-- Creating a schedule record will involve the following data
-- a. Check if there is an available Vehicle with max load weight >= Request load weight and Status is Available
-- b. Check if there is an available Driver (Status: Available)
-- c. Assign Available Vehicle and Driver to Schedule entry
-- d. Create schedule record entry with new data: date, RequestID

-- Cancelling a logistics record will involve the following data
-- a. Check if the specific logistics order exists and status is “pending”
-- b. Update Driver status to “Available”
-- c. Update Vehicle status to “Available”
-- d. Update Logistics status to “Cancelled”
-- e. Delete Request and Schedule records
-- f. Set foreign key of current record to NULL

DELETE FROM requests WHERE request_id = (SELECT r.request_id
FROM logistics l
JOIN schedules s ON l.schedule_id = s.schedule_id
JOIN requests r ON s.request_id = r.request_id
WHERE l.schedule_id = ?);

DELETE FROM schedules WHERE schedule_id = (
    SELECT s.schedule_id FROM logistics l
    JOIN schedules s ON l.schedule_id = s.schedule_id
);

SELECT d.driver_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN drivers d ON s.driver_id = d.driver_id WHERE l.schedule_id = ?);

SELECT v.vehicle_id FROM logistics l JOIN schedules s ON l.schedule_id = s.schedule_id JOIN vehicles v ON s.vehicle_id = v.vehicle_id WHERE l.schedule_id = ?);
