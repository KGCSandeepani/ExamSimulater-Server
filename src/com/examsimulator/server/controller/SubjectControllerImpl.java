/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.SubjectContoller;
import com.examsimulator.common.dto.SubjectDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.SubjectFileAccess;
import com.examsimulator.server.reservation.Reserver;
import com.examsimulator.server.service.SubjectService;
import com.examsimulator.server.service.factory.ServiceFactory;
import com.examsimulator.server.service.factoryImpl.ServiceFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Sandeepa
 */
public class SubjectControllerImpl extends UnicastRemoteObject implements SubjectContoller,Subject{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    SubjectService subjectService=(SubjectService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.SUBJECT);
    private static ArrayList<Observer> sujectObservers=new ArrayList<>();
    private static final Reserver reserver=new Reserver();
    
    public SubjectControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addSubject(SubjectDTO subject) throws RemoteException, IOException {
//        return serviceFactory.getSubjectService().addSubject(subject);
//    }

    @Override
    public boolean modifySubject(SubjectDTO subject) throws RemoteException, IOException {
        if (subjectService.modifySubject(subject)){
            notifyObservers("Modify Subject Detail!");
            return true;
        }
        return false;
    }

//    @Override
//    public boolean removeSubject(String subjectId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getSubjectService().removeSubject(subjectId);
//    }
//
//    @Override
//    public SubjectDTO searchByNameSubject(String subjectName) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getSubjectService().searchByNameSubject(subjectName);
//    }

    @Override
    public List<SubjectDTO> getAllSuject() throws RemoteException, FileNotFoundException, IOException {
        return subjectService.getAllSuject();
    }

    @Override
    public boolean reserveSubject(String subjectId) throws RemoteException {
        return reserver.reserve(subjectId, this);
    }

    @Override
    public boolean releaseSubject(String subjectId) throws RemoteException {
        return reserver.release(subjectId, this);
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        sujectObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        sujectObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
        for (Observer observer : sujectObservers) {
            new Thread(){
                public void run(){
                    try {
                        observer.update(message);
                    } catch (RemoteException ex) {
                        Logger.getLogger(SubjectControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();             
        }
    }

    @Override
    public boolean add(SubjectDTO t) throws RemoteException, IOException {
        if (subjectService.addSubject(t)){
            notifyObservers("Add new subject!");
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        if (subjectService.removeSubject(id)){
            notifyObservers("Delete Subject detail!");
            return true;
        }
        return false;
    }

    @Override
    public SubjectDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return subjectService.searchByIdSubject(string);
    }

    @Override
    public SubjectDTO searchByNameSubject(String subjectName) throws RemoteException, FileNotFoundException, IOException {
        return subjectService.searchByNameSubject(subjectName);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return subjectService.generateId();
    }
    
}
