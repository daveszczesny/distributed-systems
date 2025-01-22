package server;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * ApplicationHandler.java - this Java interface provides remote methods for user login, 
 * based on a username and password, downloading an Application Form and submitting a completed 
 * Application Form. If sucessful, the login method returns a long integer value that must be passed 
 * to the other two server methods as a session identifier. The methods to download and upload an 
 * Application Form object should use the interface type ApplicationForm as described next. Provide 
 * suitable exception handling for the remote methods to catch possible issues with parameters or 
 * method invocation e.g. InvalidCredentials, InvalidSessionID etc. 
 */

public interface ApplicationHandler extends Remote {
    long login(String username, String password) throws InvalidCredentialsException;
    ApplicationForm downloadApplicationForm(long sessionId);
    void submitApplicationForm(long sessionId, ApplicationForm applicationForm) throws FileNotFoundException;
}
