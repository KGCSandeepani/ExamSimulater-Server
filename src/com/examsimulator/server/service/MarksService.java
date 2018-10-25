/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.MarksDTO;
import com.examsimulator.server.dao.MarksFileAccess;
import com.examsimulator.server.dao.factory.FileAccessFactory;
import com.examsimulator.server.dao.factoryImpl.FileAccessFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author Lahiru Sandeepa
 */
public class MarksService extends SuperService<MarksDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private MarksFileAccess marksFileAccess=(MarksFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.MARKS);
    
    public boolean addMarks(MarksDTO marks) throws RemoteException, IOException{
        return marksFileAccess.addMarks(marks);
    }
    
    public boolean modifyMarks(MarksDTO marks) throws RemoteException, IOException {
        return marksFileAccess.modifyMarks(marks);
    }
    
    public boolean removeMarks(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
        return marksFileAccess.removeMarks(examDetailId);
    }
    
    public MarksDTO searchMarks(String examDetailId) throws RemoteException, FileNotFoundException, IOException {
        return marksFileAccess.searchMarks(examDetailId);
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return marksFileAccess.generateId();
    }
}
