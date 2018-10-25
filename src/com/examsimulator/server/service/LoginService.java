/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.LoginDTO;
import com.examsimulator.server.dao.AnswerFileAccess;
import com.examsimulator.server.dao.LoginFileAccess;
import com.examsimulator.server.dao.factory.FileAccessFactory;
import com.examsimulator.server.dao.factoryImpl.FileAccessFactoryImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author Lahiru Sandeepa
 */
public class LoginService extends SuperService<LoginDTO>{
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private LoginFileAccess loginFileAccess=(LoginFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.LOGIN);
    
    public boolean addLogin(LoginDTO loginDTO) throws RemoteException, IOException{       
        return loginFileAccess.addLogin(loginDTO);
    }
    public boolean removeLogin(String loginId) throws RemoteException, FileNotFoundException, IOException{
        return loginFileAccess.removeLogin(loginId);
    }
    
    public LoginDTO searchLogin(String loginId) throws RemoteException, FileNotFoundException, IOException{
        return loginFileAccess.searchLoginById(loginId);
    }
    public LoginDTO searchLoginByUNAndPW(String userName,String password) throws RemoteException, FileNotFoundException, IOException{
        return loginFileAccess.searchLogin(userName, password);
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return loginFileAccess.generateId();
    }
}
