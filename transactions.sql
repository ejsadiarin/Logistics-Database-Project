USE `ccinfom`;

-- Creating a schedule record will involve the following data
-- a. Check if there is an available Vehicle with max load weight >= Request load weight and Status is Available
-- b. Check if there is an available Driver (Status: Available)
-- c. Assign Available Vehicle and Driver to Schedule entry
-- d. Create schedule record entry with new data: date, RequestID

-- Cancelling a logistics record will involve the following data
-- a. Check if the specific logistics order exists and status is “pending”
-- b. Delete Request and Schedule records
-- c. Set foreign key of current record to NULL
-- d. Update Driver status to “Available”
-- e. Update Vehicle status to “Available”
-- f. Update Logistics status to “Cancelled”
