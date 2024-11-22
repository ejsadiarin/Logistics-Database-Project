# Logistics Database Project

## TODO NOTES

> [!IMPORTANT]
> - when creating a Customer, how is amount/date_paid filled? (assume since they are customer already, they "paid", so request is created simultaeneously?)
> - test the triggers
>   - [ ] trigger for schedules' date should update status (on vehicle, driver, and logistics) to "IN_TRANSIT" when passed it
>       - how to demo this?
>   - [ ] trigger for schedules' date should update status (on vehicle, driver, and logistics) to "IN_TRANSIT", 
>   - [ ] trigger for setting logistics status to arrived when vehicle becomes "AVAILABLE" from being "IN_TRANSIT"
> - implement the other transactions (put all in DAO)
>   - [ ] create new request record (requests table)
>   - [x] create new schedule record (schedules table)
>   - [x] create new logistics record (logistics table)
>   - [x] cancelling existing logistics record (logistics table)
>   - [x] removing vehicle
>   - [ ] firing driver
>   - [ ] deleting customer
> - see business rules for validation
> - how will we demo our project/user flow of system (from record creation to whole logistics process until arrived)
> - [ ] reports for driver and vehicle completed trips (both year/month)



## Project Structure

`Model`
- schema objects of the project (records) - see schema.sql
- model-specific functions

`View`
- all java swing related java files

`Controller`
- functions that is used by `View`
- connects all

## Compile and Run

- compile
```bash
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/**/*.java
# or
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/Models/*.java src/Views/*.java src/Controllers/*.java src/Database/*.java src/Services/*.java
```
```powershell
Get-ChildItem -Recurse -Path "src" -Filter "*.java" | ForEach-Object { javac -cp "lib\mysql-connector-j-9.1.0.jar;bin" -d bin $_.FullName}
```

- run
```bash
java -cp bin -p lib Views.MainFrame

# or run Main for testing: 
java -cp bin -p lib Main

# or 
java -cp bin;lib/mysql-connector-j-9.1.0.jar Main
```

## Access denied for user 'root'@'localhost' 

- login to mysql/maridb
- grant privileges
    - `... IDENTIFIED BY '<password>'` - `12345` is the password

```mysql
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '12345' WITH GRANT OPTION;
```
