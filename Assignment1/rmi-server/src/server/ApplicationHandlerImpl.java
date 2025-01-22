package server;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import exceptions.InvalidCredentialsException;

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

    private static final Map<String, String> VALID_CREDENTIALS = new HashMap<>();

    // Hardcoded valid credentials
    static {
        VALID_CREDENTIALS.put("admin", "password");
        VALID_CREDENTIALS.put("dave", "21300293");
        VALID_CREDENTIALS.put("brian", "21333461");
    }

    private Random random;

    ApplicationHandlerImpl() throws RemoteException {
        random = new Random();
    }

    @Override
    public long login(String username, String password) throws RemoteException, InvalidCredentialsException{
        // Check Username and password Separately for a more clear Exception Message for clarity
        // Extra null and extra string checks for more robustness
        if (username == null || !VALID_CREDENTIALS.containsKey(username)) {
            throw new InvalidCredentialsException("Username not found");
        }
        if (password == null || !VALID_CREDENTIALS.get(username).equals(password)) {
            throw new InvalidCredentialsException("Invalid password");
        }

        long sessionId = Math.abs(random.nextLong());
        System.out.printf("User %s logged in succesfully. Session Id: %d\n", username, sessionId);
        return sessionId;
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException{
        System.out.printf("Downloading application form to %d\n", sessionId);
        return new ApplicationFormV1();
    }

    @Override
    public void submitApplicationForm(long sessionId, ApplicationForm applicationForm) throws RemoteException, FileNotFoundException {
        System.out.printf("Submitting application form from %d \n", sessionId);
        String fileName = applicationForm.getName().replaceAll(" ", "_") + "_application_form.txt";
        PrintWriter writer = new PrintWriter(new File(fileName));
        writer.println(applicationForm.toString());
        writer.close();
        System.out.println("Application form saved to file: " + fileName);
    }
}
