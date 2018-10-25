/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.ExamDetailDTO;
import com.examsimulator.server.dao.ExamDetailFileAccess;
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
public class ExamDetailService extends SuperService<ExamDetailDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private ExamDetailFileAccess examDetailFileAccess=(ExamDetailFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.EXAMDETAIL);
    
    public boolean addExamDetail(ExamDetailDTO examDetail) throws RemoteException, IOException{
        return examDetailFileAccess.addExamDetail(examDetail);
    }
    
    public boolean removeExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
        return examDetailFileAccess.removeExamDetail(examDetailId);
    }
    
    public ExamDetailDTO searchExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
        return examDetailFileAccess.searchExamDetail(examDetailId);
    }
    
    public List<String> getQuestionId(String examId) throws RemoteException, FileNotFoundException, IOException {
        return examDetailFileAccess.getQuestionId(examId);
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return examDetailFileAccess.generateId();
    }
}
