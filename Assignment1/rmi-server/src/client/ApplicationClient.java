package client;

import java.rmi.Naming;
import java.util.Scanner;

import server.ApplicationForm;
import server.ApplicationServer;

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
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            ApplicationServer server = (ApplicationServer) Naming.lookup("//localhost/ApplicationForms");
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            long sessionId = server.appHandler.login(username, password);

            ApplicationForm form = server.appHandler.downloadApplicationForm(sessionId);

            for (int i = 0; i < form.getTotalNumberOfQuestions(); i++) {
                System.out.println(form.getQuestion(i));
                String answer = scanner.nextLine();
                form.answerQuestion(i, answer);
            }

            server.appHandler.submitApplicationForm(sessionId, form);
            System.out.println("Form submitted successfully");

            scanner.close();

        } catch (Exception e) {}
    }
}
