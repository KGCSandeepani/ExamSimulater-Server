/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.AnswerDTO;
import com.examsimulator.server.dao.AnswerFileAccess;
import com.examsimulator.server.dao.factory.FileAccessFactory;
import com.examsimulator.server.dao.factoryImpl.FileAccessFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author Lahiru Sandeepa
 */
public class AnswerService extends SuperService<AnswerDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private AnswerFileAccess answerFileAccess=(AnswerFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.ANSWER);
    public boolean addAnswer(AnswerDTO answer) throws RemoteException, IOException{       
        return answerFileAccess.addAnswer(answer);
    }
    
    public boolean modifyAnswer(AnswerDTO answer) throws RemoteException, IOException{
        return answerFileAccess.modifyTestDeveloper(answer);
    }
    
    public boolean removeAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException{
        return answerFileAccess.removeAnswer(questionId);
    }
    
    public AnswerDTO searchAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException{
        return answerFileAccess.searchAnswer(questionId);
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return answerFileAccess.generateId();
    }
}
