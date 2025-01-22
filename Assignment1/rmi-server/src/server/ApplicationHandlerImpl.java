package server;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import server.exceptions.InvalidCredentialsException;

/*
 * ApplicationHandlerImpl.java - this Java class should 
 * provide the implementation of the ApplicationHandler 
 * interface, an instance of this class will be created 
 * by the mainline server code. A single valid username 
 * and password can be hardcoded into the server for the 
 * purposes of implementing the login method.
 * 
 * The implementation method to download an ApplicationForm 
 * therefore just needs to return an instance of the 
 * ApplicationFormV1 class.
 * 
 * The CourseServer implementation method to upload a completed 
 * ApplicationForm should just save the completed form, in a 
 * suitable text format, to a file. For this purpose, 
 * the implementation class can include an appropriate toString() method.
 * The filename used should be based on the first name and second name
 * provided in the ApplicationForm.
 * 
 */

public class ApplicationHandlerImpl implements ApplicationHandler{

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "password";

    private Random random;

    ApplicationHandlerImpl() throws RemoteException {
        random = new Random();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException{
        if (
            username.equals(VALID_USERNAME) &&
            password.equals(VALID_PASSWORD)
        ) {
            System.out.println("User logged in: " + username);
            return random.nextLong();
        } else {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException{
        return new ApplicationFormV1();
    }

    @Override
    public void submitApplicationForm(long sessionId, ApplicationForm applicationForm) throws RemoteException, FileNotFoundException {
        String fileName = applicationForm.getName().replaceAll(" ", "_") + "_application_form.txt";
        PrintWriter writer = new PrintWriter(new File(fileName));
        writer.println(applicationForm.toString());
        writer.close();
    }
    
}
