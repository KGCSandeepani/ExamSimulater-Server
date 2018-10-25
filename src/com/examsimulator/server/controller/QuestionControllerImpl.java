/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.QuestionController;
import com.examsimulator.common.dto.QuestionDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.QuestionFileAccess;
import com.examsimulator.server.reservation.Reserver;
import com.examsimulator.server.service.QuestionService;
import com.examsimulator.server.service.factory.ServiceFactory;
import com.examsimulator.server.service.factoryImpl.ServiceFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lahiru Sandeepa
 */
public class QuestionControllerImpl extends UnicastRemoteObject implements QuestionController,Subject{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    QuestionService questionService=(QuestionService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.QUESTION);
    private static ArrayList<Observer> questionObservers=new ArrayList<>();
    private static final Reserver reserver=new Reserver();
    
    public QuestionControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addQuestion(QuestionDTO question) throws RemoteException, IOException {
//        return serviceFactory.getQuestionService().addQuestion(question);
//    }

    @Override
    public boolean modifyQuestion(QuestionDTO question) throws RemoteException, IOException {
        return questionService.modifyQuestion(question);
    }

//    @Override
//    public boolean removeQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getQuestionService().removeQuestion(questionId);
//    }
//
//    @Override
//    public QuestionDTO searchQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getQuestionService().searchQuestion(questionId);
//    }

    @Override
    public List<QuestionDTO> getAllQuestion() throws RemoteException, FileNotFoundException, IOException {
        return questionService.getAllQuestion();
    }
    
    @Override
    public List<QuestionDTO> getSelectedQuestion(String subjectId,String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
        return questionService.getSelectedQuestion(subjectId, testDeveloperId);
    }
    
    @Override
    public List<QuestionDTO> getQuestion(String subjectId) throws RemoteException, FileNotFoundException, IOException {
        return questionService.getQuestion(subjectId);
    }

    @Override
    public int calculateTime(List<String> questionIdList) throws RemoteException, FileNotFoundException, IOException {
        return questionService.calculateTime(questionIdList);
    }
    
    @Override
    public boolean reserveQuestion(String questionId) throws RemoteException {
        return reserver.reserve(questionId, this);
    }

    @Override
    public boolean releaseQuestion(String questionId) throws RemoteException {
        return reserver.release(questionId, this);
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        questionObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        questionObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
        for (Observer observer : questionObservers) {
            observer.update(message);
        }
    }

    @Override
    public boolean add(QuestionDTO t) throws RemoteException, IOException {
        return questionService.addQuestion(t);
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return questionService.removeQuestion(id);
    }

    @Override
    public QuestionDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return questionService.searchQuestion(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return questionService.generateId();
    }
    
}
