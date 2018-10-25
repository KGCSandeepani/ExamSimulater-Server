/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service;

import com.examsimulator.common.dto.TestDeveloperDTO;
import com.examsimulator.server.dao.TestDeveloperFileAccess;
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
public class TestDeveloperService extends SuperService<TestDeveloperDTO>{
    
    private FileAccessFactory fileAccessFactory=FileAccessFactoryImpl.getFileAccess();
    private TestDeveloperFileAccess testDeveloperFileAccess=(TestDeveloperFileAccess) fileAccessFactory.getFileAccess(FileAccessFactory.FileAccessTypes.TESTDEVELOPER);
    
    public boolean addTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, IOException {
        return testDeveloperFileAccess.addTestDeveloper(testDeveloper);
    }
    
    public boolean modifyTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperFileAccess.modifyTestDeveloper(testDeveloper);
    }
    
    public boolean removeTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperFileAccess.removeTestDeveloper(testDeveloperId);
    }
    
    public TestDeveloperDTO searchByIdTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperFileAccess.searchByIdTestDeveloper(testDeveloperId);
    }
    
    public TestDeveloperDTO searchByNameTestDeveloper(String testDeveloperName) throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperFileAccess.searchByNameTestDeveloper(testDeveloperName);
    }
    
    public List<TestDeveloperDTO> getAllTestDeveloper() throws RemoteException, FileNotFoundException, IOException {
        return testDeveloperFileAccess.getAllTestDeveloper();
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        return testDeveloperFileAccess.generateId();
    }
}
