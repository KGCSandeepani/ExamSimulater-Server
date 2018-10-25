/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.ExamDTO;
import com.examsimulator.server.dao.ExamFileAccess;
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
public class ExamService extends SuperService<ExamDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private ExamFileAccess examFileAccess=(ExamFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.EXAM);
    
    public boolean addExam(ExamDTO exam) throws RemoteException, IOException {
        return examFileAccess.addTestDeveloper(exam);
    }
    
    public boolean removeExam(String examId) throws RemoteException, FileNotFoundException, IOException {
        return examFileAccess.removeExam(examId);
    }
    
    public ExamDTO searchExamByExamId(String examId) throws RemoteException, FileNotFoundException, IOException {
        return examFileAccess.searchExamByExamId(examId);
    }
    
    public List<ExamDTO> searchExamStudentId(String studentId) throws RemoteException, FileNotFoundException, IOException {
        return examFileAccess.searchExamByStudentId(studentId);
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return examFileAccess.generateId();
    }
    
    
}
