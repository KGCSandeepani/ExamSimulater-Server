/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.ExamController;
import com.examsimulator.common.dto.ExamDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.ExamFileAccess;
import com.examsimulator.server.service.ExamService;
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
public class ExamControllerImpl extends UnicastRemoteObject implements ExamController{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    ExamService examService=(ExamService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.EXAM);
    private static ArrayList<Observer> examObservers=new ArrayList<>();
    
    public ExamControllerImpl()throws RemoteException{
        
    }
//    @Override
//    public boolean addExam(ExamDTO exam) throws RemoteException, IOException {
//        return serviceFactory.getExamService().addExam(exam);
//    }
//
//    @Override
//    public boolean removeExam(String examId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getExamService().removeExam(examId);
//    }
//
//    @Override
//    public ExamDTO searchExamByExamId(String examId) throws RemoteException, FileNotFoundException, IOException {
//        return serviceFactory.getExamService().searchExamByExamId(examId);
//    }

    @Override
    public List<ExamDTO> searchExamStudentId(String studentId) throws RemoteException, FileNotFoundException, IOException {
        return examService.searchExamStudentId(studentId);
    }

//    @Override
//    public void registerObserver(Observer observer) throws RemoteException {
//        examObservers.add(observer);
//    }
//
//    @Override
//    public void unregisterObserver(Observer observer) throws RemoteException {
//        examObservers.remove(observer);
//    }
//
//    @Override
//    public void notifyObservers(String message) throws RemoteException {
//        for (Observer observer : examObservers) {
//            observer.update(message);
//        }
//    }

    @Override
    public boolean add(ExamDTO t) throws RemoteException, IOException {
        return examService.addExam(t);
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        return examService.removeExam(id);
    }

    @Override
    public ExamDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return examService.searchExamByExamId(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return examService.generateId();
    }

    
    
}
