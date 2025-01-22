package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/*
 * ApplicationServer.java - this Java class should 
 * provide the mainline server code. This should 
 * create an instance of the ApplicationHandlerImpl 
 * class and then register this with the RMIRegistry 
 * so that it can be located and used by the client code.
 */

public class ApplicationServer {
    

    ApplicationServer() throws RemoteException{
        super();
    }

    public static void main(String[] args){
        try {
            ApplicationHandler appHandler = new ApplicationHandlerImpl();

            ApplicationHandler stub = (ApplicationHandler) UnicastRemoteObject.exportObject(appHandler, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ApplicationForms", stub);
            System.err.println("Server ready");
        } catch (Exception ex) {
            System.err.println("Server exception: " + ex.toString());
            ex.printStackTrace();
        }
    }
}
