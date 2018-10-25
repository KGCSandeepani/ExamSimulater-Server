/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.StudentDTO;
import com.examsimulator.server.dao.StudentFileAccess;
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
public class StudentService extends SuperService<StudentDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private StudentFileAccess studentFileAccess=(StudentFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.STUDENT);
    
    public boolean addStudent(StudentDTO student) throws RemoteException, IOException {
        return studentFileAccess.addStudent(student);
    }
    
    public boolean modifyStudent(StudentDTO student) throws RemoteException, FileNotFoundException, IOException {
        return studentFileAccess.modifyStudent(student);
    }
    
    public boolean removeStudent(String studentId) throws RemoteException, FileNotFoundException,IOException {
        return studentFileAccess.removeStudent(studentId);
    }
    
    public StudentDTO searchByIdStudent(String studentId) throws RemoteException, FileNotFoundException,IOException {
        return studentFileAccess.searchByIdStudent(studentId);
    }
    
    public StudentDTO searchByNameStudent(String studentName) throws RemoteException, FileNotFoundException, IOException {
        return studentFileAccess.searchByNameStudent(studentName);
    }
    
    public List<StudentDTO> getAllStudent() throws RemoteException, FileNotFoundException, IOException {
        return studentFileAccess.getAllStudent();
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return studentFileAccess.generateId();
    }
}
