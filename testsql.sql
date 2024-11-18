SELECT * FROM customers;
SELECT * FROM drivers;
SELECT * FROM vehicles;
SELECT * FROM logistics;
SELECT * FROM schedules;
SELECT * FROM requests;

SELECT * FROM drivers WHERE status = 'AVAILABLE';
SELECT * FROM vehicles WHERE status = 'AVAILABLE';

SELECT s.date, s.driver_id, d.full_name, d.status FROM logistics l
JOIN schedules s ON l.schedule_id = s.schedule_id
JOIN drivers d ON s.driver_id = d.driver_id
WHERE d.status = 'AVAILABLE';
