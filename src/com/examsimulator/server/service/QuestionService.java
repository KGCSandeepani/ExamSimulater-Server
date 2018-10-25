/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.QuestionDTO;
import com.examsimulator.server.dao.QuestionFileAccess;
import com.examsimulator.server.dao.factory.FileAccessFactory;
import com.examsimulator.server.dao.factoryImpl.FileAccessFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Lahiru Sandeepa
 */
public class QuestionService extends SuperService<QuestionDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private QuestionFileAccess questionFileAccess=(QuestionFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.QUESTION);
    
    public boolean addQuestion(QuestionDTO question) throws RemoteException, IOException {
        return questionFileAccess.addQuestion(question);
    }
    
    public boolean modifyQuestion(QuestionDTO question) throws RemoteException, IOException {
        return questionFileAccess.modifyQuestion(question);
    }
    
    public boolean removeQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.removeQuestion(questionId);
    }
    
    public QuestionDTO searchQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.searchQuestion(questionId);
    }
    
    public List<QuestionDTO> getAllQuestion() throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.getAllQuestion();
    }
    
    public List<QuestionDTO> getSelectedQuestion(String subjectId, String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.getSelectedQuestion(subjectId, testDeveloperId);
    }
    
    public List<QuestionDTO> getQuestion(String subjectId) throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.getQuestion(subjectId);
    }
    
    public int calculateTime(List<String> questionIdList) throws RemoteException, FileNotFoundException, IOException {
        return questionFileAccess.calculateTime(questionIdList);
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return questionFileAccess.generateId();
    }
}
