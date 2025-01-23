package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidAnswerException;
import exceptions.InvalidQuestionIndexException;

/*
 * ApplicationFormV1.java - this Java class should provide an
 * implementation of the ApplicationForm interface.  It should
 * include questions that allow the following information about 
 * the applicant to be included in a completed application form: 
 * name, address, email and contact number of the applicant. 
 * It should also have a question that allows an applicant to provide 
 * a personal statement e.g. this could be used to include additional 
 * details about the applicant and a summary of their existing 
 * qualifications or results.
 */

public class ApplicationFormV1 implements ApplicationForm, Serializable {

    private Map<Integer, String> questions;
    private Map<Integer, String> answers;

    public ApplicationFormV1() {
        questions = new HashMap<>();
        answers = new HashMap<>();
        questions.put(0, "Full Name:");
        questions.put(1, "Address:");
        questions.put(2, "Email:");
        questions.put(3, "Contact Number:");
        questions.put(4, "Personal Statement:");
    }

    @Override
    public String getApplicationFormInfo() throws RemoteException {
        return toString();
    }

    @Override
    public int getTotalNumberOfQuestions() throws RemoteException {
        return questions.size();
    }

    @Override
    public String getQuestion(int questionIndex) throws RemoteException, InvalidQuestionIndexException {
        if (questionIndex < 0 || questionIndex >= questions.size()){
            throw new InvalidQuestionIndexException("Invalid question index");
        }
        return questions.get(questionIndex);
    }

    @Override
    public void answerQuestion(int questionIndex, String answer) throws RemoteException, InvalidQuestionIndexException, InvalidAnswerException {
        if (answer == null || answer.isEmpty()) {
            throw new InvalidAnswerException("Invalid answer provided. Answer must not be null");
        }

        verifyAnswer(questionIndex, answer);

        answers.put(questionIndex, answer);
    }

    @Override
    public String getName() throws RemoteException{
        return answers.get(0);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            sb.append(questions.get(i)).append(" ");

            // Checks if the answer was provided by the user
            // if not it will append "N/A" to the string
            if (i < answers.size()){
                sb.append(answers.get(i));
            } else {
                sb.append("N/A");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void verifyAnswer(int questionIndex, String answer) throws InvalidAnswerException {
        // This is a check to ensure that the full name is provided
        if (questionIndex == 0 && !answer.contains(" ")) {
            throw new InvalidAnswerException("Invalid name provided. First and last name must be provided");
        }

        // Checks if the email provided is in a valid format
        else if (questionIndex == 2 && !isValidEmail(answer)) {
            throw new InvalidAnswerException("Invalid email provided. Email must be in a valid format");
        }

        else if (questionIndex == 3 && !isValidContactNumber(answer)) {
            throw new InvalidAnswerException("Invalid contact number provided. Contact number must be 10 digits long");
        }

    }

    private boolean isValidContactNumber(String number) {
        String numberRegex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(numberRegex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
