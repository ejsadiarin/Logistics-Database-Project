# Logistics Database Project

## Project Structure

`Model`
- schema objects of the project (records) - see schema.sql
- model-specific functions

`View`
- all java swing related java files

`Controller`
- functions that is used by `View`
- connects all

## Installation

```bash
git clone https://github.com/ejsadiarin/Logistics-Database-Project.git
```

## Compile and Run

- compile
```bash
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/**/*.java
# or
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/Models/*.java src/Views/*.java src/Controllers/*.java src/Database/*.java src/Services/*.java
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
