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

## Compile and Run

```bash
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/**/*.java
java -cp bin -p lib Main
# or java -cp bin;lib/mysql-connector-j-9.1.0.jar Main
```

- other
```bash
sudo java -cp bin -p lib Main
```

## Access denied for user 'root'@'localhost' 

- login to mysql/maridb
- grant privileges
    - `... IDENTIFIED BY '<password>'` - `12345` is the password

```mysql
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY '12345' WITH GRANT OPTION;
```
