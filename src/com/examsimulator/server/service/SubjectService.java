/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.SubjectDTO;
import com.examsimulator.server.dao.SubjectFileAccess;
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
public class SubjectService extends SuperService<SubjectDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private SubjectFileAccess subjectFileAccess=(SubjectFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.SUBJECT);
    
    public boolean addSubject(SubjectDTO subject) throws RemoteException, IOException {
        return subjectFileAccess.addSubject(subject);
    }
    
    public boolean modifySubject(SubjectDTO subject) throws RemoteException, IOException {
        return subjectFileAccess.modifySubject(subject);
    }
    
    public boolean removeSubject(String subjectId) throws RemoteException, FileNotFoundException, IOException {
        return subjectFileAccess.removeSubject(subjectId);
    }
    
    public SubjectDTO searchByIdSubject(String subjectId) throws RemoteException, FileNotFoundException, IOException {
        return subjectFileAccess.searchByIdSubject(subjectId);
    }
    
    public SubjectDTO searchByNameSubject(String subjectName) throws RemoteException, FileNotFoundException, IOException {
        return subjectFileAccess.searchByNameSubject(subjectName);
    }
    
    public List<SubjectDTO> getAllSuject() throws RemoteException, FileNotFoundException, IOException {
        return subjectFileAccess.getAllSubject();
    }    
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return subjectFileAccess.generateId();
    }
}
