/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.MarksController;
import com.examsimulator.common.dto.MarksDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.MarksFileAccess;
import com.examsimulator.server.service.MarksService;
import com.examsimulator.server.service.factory.ServiceFactory;
import com.examsimulator.server.service.factoryImpl.ServiceFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author Lahiru Sandeepa
 */
public class MarksControllerImpl extends UnicastRemoteObject implements MarksController{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    MarksService marksService=(MarksService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.MARKS);
    private static ArrayList<Observer> marksObservers=new ArrayList<>();
    
    public MarksControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addMarks(MarksDTO marks) throws RemoteException, IOException {
//        return serviceFactory.getmMarksService().addMarks(marks);
//    }

    @Override
    public boolean modifyMarks(MarksDTO marks) throws RemoteException, IOException {
        return marksService.modifyMarks(marks);
    }

//    @Override
//    public boolean removeMarks(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getmMarksService().removeMarks(examDetailId);
//    }
//
//    @Override
//    public MarksDTO searchMarks(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getmMarksService().searchMarks(examDetailId);
//    }

    

//    @Override
//    public void registerObserver(Observer observer) throws RemoteException {
//        marksObservers.add(observer);
//    }
//
//    @Override
//    public void unregisterObserver(Observer observer) throws RemoteException {
//        marksObservers.remove(observer);
//    }
//
//    @Override
//    public void notifyObservers(String message) throws RemoteException {
//        for (Observer observer : marksObservers) {
//            observer.update(message);
//        }
//    }

    @Override
    public boolean add(MarksDTO t) throws RemoteException, IOException {
        return marksService.addMarks(t);
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return marksService.removeMarks(id);
    }

    @Override
    public MarksDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return marksService.searchMarks(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return marksService.generateId();
    }
    
}
