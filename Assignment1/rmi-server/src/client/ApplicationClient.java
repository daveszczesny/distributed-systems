package client;

import java.rmi.Naming;
import java.util.Scanner;

import exceptions.InvalidCredentialsException;
import server.ApplicationForm;
import server.ApplicationHandler;

/*
 * ApplicationClient.java - this Java class should provide the client code.
 * This can done as a simple command line client program that will interact
 * with the server as follows: 
 * (i) Login to the server.
 * (ii) Download an ApplicationForm object.
 * (iii) Call methods on the ApplicationForm, as required to complete the form.
 * (iv) Submit the completed Application Form back to the sever.
 */

public class ApplicationClient {

    private static void printErrorAndExit(String message) {
        System.out.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            ApplicationHandler handler = (ApplicationHandler) Naming.lookup("//localhost/ApplicationForms");
            
            System.out.println("Welcome to the Application Form System");
            System.out.println("Please login to continue");

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            System.out.printf("Logging in as %s with password %s \n", username, password);

            long sessionId = -1;

            try {
                sessionId = handler.login(username, password);
            } catch (InvalidCredentialsException ex) {
                printErrorAndExit("Invalid credentials. Please try again.");
            } catch (Exception e) {
                printErrorAndExit("An error occurred. Please try again.");
            }

            if (sessionId < 0) {
                printErrorAndExit("An error occurred. Please try again.");
            }

            System.out.println("Successfully logged in!");

            ApplicationForm form = handler.downloadApplicationForm(sessionId);

            System.out.println("Please fill out the form below:");

            for (int i = 0; i < form.getTotalNumberOfQuestions(); i++) {
                System.out.println(form.getQuestion(i));
                String answer = scanner.nextLine();
                form.answerQuestion(i, answer);
                System.out.printf("\n");
            }

            try{
                handler.submitApplicationForm(sessionId, form);
            } catch (Exception e) {
                System.out.println(e);
                printErrorAndExit("An error occurred. Please try again.");
            }
            System.out.println("Form submitted successfully");

            scanner.close();

        } catch (Exception e) {}
    }
}
