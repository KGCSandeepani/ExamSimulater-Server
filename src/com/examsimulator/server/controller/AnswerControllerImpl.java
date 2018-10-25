/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.AnswerController;
import com.examsimulator.common.dto.AnswerDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.AnswerFileAccess;
import com.examsimulator.server.reservation.Reserver;
import com.examsimulator.server.service.AnswerService;
import com.examsimulator.server.service.factory.ServiceFactory;
import com.examsimulator.server.service.factoryImpl.ServiceFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Sandeepa
 */
public class AnswerControllerImpl extends UnicastRemoteObject implements AnswerController,Subject{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    AnswerService answerService=(AnswerService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.ANSWER);
    private static ArrayList<Observer> answerObservers = new ArrayList<>();
    
    public AnswerControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addAnswer(AnswerDTO answer) throws RemoteException, IOException {
//        return serviceFactory.getAnswerService().addAnswer(answer);
//    }

    @Override
    public boolean modifyAnswer(AnswerDTO answer) throws RemoteException, IOException {
        if (answerService.modifyAnswer(answer)){
            notifyObservers("Answer Detail modify!");
            return true;
        }
        return false;
    }

//    @Override
//    public boolean removeAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getAnswerService().removeAnswer(questionId);
//    }
//
//    @Override
//    public AnswerDTO searchAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getAnswerService().searchAnswer(questionId);
//    }

    @Override
    public boolean reserveAnswer(String answerId) throws RemoteException {
        return true;
    }

    @Override
    public boolean releaseAnswer(String answerId) throws RemoteException {
        return true;
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        answerObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        answerObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
        for (Observer observer : answerObservers) {
            new Thread(){
                public void run(){
                    try {
                        observer.update(message);
                    } catch (RemoteException ex) {
                        Logger.getLogger(AnswerControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();                
        }
    }

    @Override
    public boolean add(AnswerDTO t) throws RemoteException, IOException {        
        return answerService.addAnswer(t);        
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return answerService.removeAnswer(id);
    }

    @Override
    public AnswerDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return answerService.searchAnswer(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return answerService.generateId();
    }
    
}
