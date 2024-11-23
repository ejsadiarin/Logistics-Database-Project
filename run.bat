@ECHO OFF
javac -cp lib/mysql-connector-j-9.1.0.jar -d bin src/*.java src/Models/*.java src/Views/*.java src/Controllers/*.java src/Database/*.java src/Services/*.java
java -cp bin -p lib Views.MainFrame