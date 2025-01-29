package server;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import exceptions.InvalidCredentialsException;
import exceptions.InvalidSessionException;
import server.models.Session;

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

    private List<Session> sessions = new ArrayList<>();
    private long sessionExpiryTime = 60_000;

    // Hardcoded valid credentials
    static {
        VALID_CREDENTIALS.put("admin", "password");
        VALID_CREDENTIALS.put("dave", "21300293");
        VALID_CREDENTIALS.put("brian", "21333461");
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

        try{
        Session session = new Session();
        sessions.add(session);
        System.out.printf("User %s logged in succesfully. Session Id: %d\n", username, session.getId());
        return session.getId();
        }catch(Exception e) {
            System.out.println("Error creating session");
        }

        throw new InvalidCredentialsException("Error creating session");

        
    }

    @Override
    public ApplicationForm downloadApplicationForm(long sessionId) throws RemoteException, InvalidSessionException{

        // Check if session is valid
        if (!isSessionValid(sessionId)) throw new InvalidSessionException("Session is invalid or expired");

        System.out.printf("Downloading application form to %d\n", sessionId);
        return new ApplicationFormV1();
    }

    @Override
    public void submitApplicationForm(long sessionId, ApplicationForm applicationForm) throws RemoteException, FileNotFoundException, InvalidSessionException {
        
        // Check if session is valid
        if(!isSessionValid(sessionId)) throw new InvalidSessionException("Session is invalid or expired");

        System.out.printf("Submitting application form from %d \n", sessionId);

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(
            applicationForm.getName().replaceAll(" ", "_")
        );
        
        fileNameBuilder.append("_");
        fileNameBuilder.append(String.valueOf(sessionId).substring(0, 5));
        fileNameBuilder.append("_application_form.txt");

        PrintWriter writer = new PrintWriter(new File(fileNameBuilder.toString()));
        writer.println(applicationForm.toString());
        writer.close();
        System.out.println("Application form saved to file: " + fileNameBuilder.toString());
    }

    @Override
    public boolean isSessionValid(long sessionId) throws RemoteException {
        Session currentSession = sessions.stream().filter(s -> s.getId() == sessionId).findFirst().orElse(null);
        if (currentSession == null) {
            System.out.printf("Session %d not found\n", sessionId);
            return false;
        } else if (currentSession.getSessionStarted() + sessionExpiryTime < System.currentTimeMillis()) {
            System.out.printf("Session %d has expired\n", sessionId);
            this.sessions.remove(currentSession);
            return false;
        }
        return true;
    }
}
