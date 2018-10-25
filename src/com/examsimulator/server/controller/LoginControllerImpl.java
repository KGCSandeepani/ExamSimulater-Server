/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.LoginController;
import com.examsimulator.common.dto.LoginDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.service.LoginService;
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
public class LoginControllerImpl extends UnicastRemoteObject implements LoginController{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    LoginService loginService=(LoginService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.LOGIN);
    private static ArrayList<Observer> loginObservers = new ArrayList<>();
    
    public LoginControllerImpl()throws RemoteException{
        
    }
    
    @Override
    public boolean add(LoginDTO t) throws RemoteException, IOException {
        return loginService.addLogin(t);
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return loginService.removeLogin(id);
    }

    @Override
    public LoginDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return loginService.searchLogin(string);
    }

//    @Override
//    public void registerObserver(Observer observer) throws RemoteException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void unregisterObserver(Observer observer) throws RemoteException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void notifyObservers(String message) throws RemoteException {
//        for (Observer observer : loginObservers) {
//            observer.update(message);
//        }
//    }

    @Override
    public LoginDTO searchLoginByUNAndPW(String userName,String password) throws RemoteException, IOException, FileNotFoundException {
        return loginService.searchLoginByUNAndPW(userName, password);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return loginService.generateId();
    }
    
}
