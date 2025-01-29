# To Run the Code

## First build the java project
1. Go into rmi-server directory.
2. Run the following command.
```bash
sh build.sh
```
or run the actual command
```bash
javac -d bin -sourcepath src src/server/*.java src/server/*.java src/client/*.java
```

## Register RMI
1. Go into bin directory.
```bash
cd bin
```
2. Run the following command in bin directory.
```bash
rmiregistry 1099
```

## Run Server
1. Ensure you are in rmi-server directory
2. Run the following command
```bash
sh start-server.sh
```
or
```bash
java -cp bin server.ApplicationServer
```
3. Ensure 'Server Ready' command is printed

## Run Client
1. Ensure you are in rmi-server directory
2. Run the following command
```bash
sh start-client.sh
```
or
```bash
java -cp bin client.ApplicaitonClient
```
3. The valid usernames are the two authors and their ID numbers or 'admin' and 'password'
