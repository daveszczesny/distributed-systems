package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.naming.InvalidNameException;

import exceptions.InvalidAnswerException;
import exceptions.InvalidQuestionIndexException;

/*
 * ApplicationForm.java - this Java interface provides methods for 
 * retrieving and answering questions. The interface should have a 
 * method to retrieve general information about the application form 
 * and a method to return the total number of questions to be answered. 
 * It should also have methods to retrieve and answer questions, based 
 * on the question number, it only needs to support text type questions and answers.
 */

public interface ApplicationForm extends Remote {
    String getApplicationFormInfo() throws RemoteException;
    int getTotalNumberOfQuestions() throws RemoteException;
    String getQuestion(int questionIndex) throws RemoteException, InvalidQuestionIndexException;
    void answerQuestion(int questionIndex, String answer) throws RemoteException, InvalidQuestionIndexException, InvalidAnswerException, InvalidNameException;
    String getName() throws RemoteException;
}
