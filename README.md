# Logistics Database Project

## Project Structure

`Model`
- schema objects of the project (records) - see schema.sql

`View`
- all java swing related java files

`Controller`
- functions that is used by `View`
- connects all

## Compile and Run

```bash
javac -cp lib/mysql-connector-j-<version>.jar -d bin src/*.java
java -cp bin;lib/mysql-connector-j-<version>.jar MySQLSelectExample
```
