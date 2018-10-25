/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.ExamDetailController;
import com.examsimulator.common.dto.ExamDetailDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.ExamDetailFileAccess;
import com.examsimulator.server.service.ExamDetailService;
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
public class ExamDetailControllerImpl extends UnicastRemoteObject implements ExamDetailController{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    ExamDetailService examDetailService=(ExamDetailService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.EXAMDETAIL);
    private static ArrayList<Observer> examDetailObservers=new ArrayList<>();
    
    public ExamDetailControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addExamDetail(ExamDetailDTO examDetail) throws RemoteException, IOException {
//        return serviceFactory.getExamDetailService().addExamDetail(examDetail);
//    }
//
//    @Override
//    public boolean removeExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getExamDetailService().removeExamDetail(examDetailId);
//    }
//
//    @Override
//    public ExamDetailDTO searchExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getExamDetailService().searchExamDetail(examDetailId);
//    }

    @Override
    public List<String> getQuestionId(String examId) throws RemoteException, FileNotFoundException, IOException {
        return examDetailService.getQuestionId(examId);
    }

//    @Override
//    public void registerObserver(Observer observer) throws RemoteException {
//        examDetailObservers.add(observer);
//    }
//
//    @Override
//    public void unregisterObserver(Observer observer) throws RemoteException {
//        examDetailObservers.remove(observer);
//    }
//
//    @Override
//    public void notifyObservers(String message) throws RemoteException {
//        for (Observer observer : examDetailObservers) {
//            observer.update(message);
//        }
//    }

    @Override
    public boolean add(ExamDetailDTO t) throws RemoteException, IOException {
        return examDetailService.addExamDetail(t);
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return examDetailService.removeExamDetail(id);
    }

    @Override
    public ExamDetailDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return examDetailService.searchExamDetail(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return examDetailService.generateId();
    }
    
}
