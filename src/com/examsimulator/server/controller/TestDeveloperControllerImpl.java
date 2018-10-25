/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.TestDeveloperController;
import com.examsimulator.common.dto.TestDeveloperDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.TestDeveloperFileAccess;
import com.examsimulator.server.reservation.Reserver;
import com.examsimulator.server.service.TestDeveloperService;
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
public class TestDeveloperControllerImpl extends UnicastRemoteObject implements TestDeveloperController,Subject{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    TestDeveloperService testDeveloperService=(TestDeveloperService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.TESTDEVELOPER);
    private static ArrayList<Observer> testDeveloperObservers=new ArrayList<>();
    private static final Reserver reserver=new Reserver();
    
    public TestDeveloperControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, IOException {
//        return serviceFactory.getTestDeveloperService().addTestDeveloper(testDeveloper);
//    }

    @Override
    public boolean modifyTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, FileNotFoundException, IOException {
        if (testDeveloperService.modifyTestDeveloper(testDeveloper)){
            notifyObservers("Modify Test Developer detail");
            return true;
        }
        return false;
    }

//    @Override
//    public boolean removeTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getTestDeveloperService().removeTestDeveloper(testDeveloperId);
//    }
//
//    @Override
//    public TestDeveloperDTO searchByIdTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getTestDeveloperService().searchByIdTestDeveloper(testDeveloperId);
//    }

    @Override
    public TestDeveloperDTO searchByNameTestDeveloper(String testDeveloperName) throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperService.searchByNameTestDeveloper(testDeveloperName);
    }

    @Override
    public List<TestDeveloperDTO> getAllTestDeveloper() throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperService.getAllTestDeveloper();
    }

    @Override
    public boolean reserveTestDeveloper(String testDeveloperId) throws RemoteException {
        return reserver.reserve(testDeveloperId, this);
    }

    @Override
    public boolean releaseTestDeveloper(String testDeveloperId) throws RemoteException {
        return reserver.release(testDeveloperId, this);
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        testDeveloperObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        testDeveloperObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
        for (Observer observer : testDeveloperObservers) {
            new Thread(){
                public void run(){
                    try {
                        observer.update(message);
                    } catch (RemoteException ex) {
                        Logger.getLogger(TestDeveloperControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();            
        }
    }

    @Override
    public boolean add(TestDeveloperDTO t) throws RemoteException, IOException {
        if (testDeveloperService.addTestDeveloper(t)){
            notifyObservers("Add new Test Developer!");
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        if (testDeveloperService.removeTestDeveloper(id)){
            notifyObservers("Delete Test Developer detail!");
            return true;
        }
        return false;
    }

    @Override
    public TestDeveloperDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return testDeveloperService.searchByIdTestDeveloper(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperService.generateId();
    }
    
}
