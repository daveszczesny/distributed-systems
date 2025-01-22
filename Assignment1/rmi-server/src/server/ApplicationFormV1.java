package server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InvalidNameException;

import server.exceptions.InvalidAnswerException;
import server.exceptions.InvalidQuestionIndexException;

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

public class ApplicationFormV1 implements ApplicationForm{

    private Map<Integer, String> questions;
    private Map<Integer, String> answers;

    public ApplicationFormV1() {
        questions = new HashMap<>();
        answers = new HashMap<>();
        questions.put(0, "Name:");
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
        if (questionIndex < 1 || questionIndex > questions.size()){
            throw new InvalidQuestionIndexException("Invalid question index");
        }
        return questions.get(questionIndex);
    }

    @Override
    public void answerQuestion(int questionIndex, String answer) throws RemoteException, InvalidQuestionIndexException, InvalidAnswerException, InvalidNameException {
        if (questionIndex < 1 || questionIndex > questions.size()) {
            throw new InvalidQuestionIndexException("Invalid question index");
        } else if (answer == null || answer.isEmpty()) {
            throw new InvalidAnswerException("Invalid answer provided. Answer must not be null");
        }

        if (questionIndex == 0 && !answer.contains(" ")) {
            throw new InvalidNameException("Invalid name provided. First and last name must be provided");
        }

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
        }

        return sb.toString();
    }

}
