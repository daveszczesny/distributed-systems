# To Run the Code

## First build the java project
1. Go into rmi-server directory.
2. Run the following command.
```bash
javac -d bin -sourcepath src src/server/*.java src/server/exceptions/*.java src/client/*.java
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
java -cp bin server.ApplicationServer
```
3. Ensure 'Server Ready' command is printed

## Run Client
1. Ensure you are in rmi-server directory
2. Run the following command
```bash
java -cp bin client.ApplicaitonClient
```
