/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller;

import com.examsimulator.common.controller.StudentController;
import com.examsimulator.common.dto.StudentDTO;
import com.examsimulator.common.observer.Observer;
import com.examsimulator.common.observer.Subject;
import com.examsimulator.server.dao.StudentFileAccess;
import com.examsimulator.server.reservation.Reserver;
import com.examsimulator.server.service.StudentService;
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
public class StudentControllerImpl extends UnicastRemoteObject implements StudentController,Subject{

    ServiceFactory serviceFactory=ServiceFactoryImpl.getServiceFactory();
    StudentService studentService=(StudentService) serviceFactory.getSevice(ServiceFactory.ServiceTypes.STUDENT);
    private static ArrayList<Observer> studentObservers=new ArrayList<>();
    private static final Reserver reserver=new Reserver();
    
    public StudentControllerImpl()throws RemoteException{
        
    }
    
//    @Override
//    public boolean addStudent(StudentDTO student) throws RemoteException, IOException {
//        return serviceFactory.getStudentService().addStudent(student);
//    }

    @Override
    public boolean modifyStudent(StudentDTO student) throws RemoteException, FileNotFoundException, IOException {
        if (studentService.modifyStudent(student)){
            notifyObservers("Modify student!");
            return true;
        }
        return true;
    }

//    @Override
//    public boolean removeStudent(String studentId) throws RemoteException, FileNotFoundException,IOException {
//        return serviceFactory.getStudentService().removeStudent(studentId);
//    }
//
//    @Override
//    public StudentDTO searchByIdStudent(String studentId) throws RemoteException, FileNotFoundException,IOException {
//        return serviceFactory.getStudentService().searchByIdStudent(studentId);
//    }

    @Override
    public StudentDTO searchByNameStudent(String studentName) throws RemoteException, FileNotFoundException, IOException {
        return studentService.searchByNameStudent(studentName);
    }

    @Override
    public List<StudentDTO> getAllStudent() throws RemoteException, FileNotFoundException, IOException {
        return studentService.getAllStudent();
    }

    @Override
    public boolean reserveStudent(String studentId) throws RemoteException {
        return reserver.reserve(studentId, this);
    }

    @Override
    public boolean releaseStudent(String studentId) throws RemoteException {
        return reserver.release(studentId, this);
    }

    @Override
    public void registerObserver(Observer observer) throws RemoteException {
        studentObservers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws RemoteException {
        studentObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
        for (Observer observer : studentObservers) {
            new Thread(){
                public void run(){
                    try {
                        observer.update(message);
                    } catch (RemoteException ex) {
                        Logger.getLogger(StudentControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();            
        }
    }

    @Override
    public boolean add(StudentDTO t) throws RemoteException, IOException {
        if (studentService.addStudent(t)) {
            notifyObservers("Add new Student!");
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) throws RemoteException, IOException, FileNotFoundException {
        if (studentService.removeStudent(id)){
            notifyObservers("Delete student!");
            return true;
        }
        return false;
    }

    @Override
    public StudentDTO search(String string) throws RemoteException, IOException, FileNotFoundException {
        return studentService.searchByIdStudent(string);
    }

    @Override
    public String generateId() throws RemoteException, FileNotFoundException, IOException {
        return studentService.generateId();
    }
    
}
