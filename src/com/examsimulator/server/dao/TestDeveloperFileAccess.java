/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.TestDeveloperDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Lahiru Sandeepa
 */
public class TestDeveloperFileAccess extends SuperFileAccess<TestDeveloperDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/TestDeveloper.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(testDeveloper.getTestDeveloperId()+"@"+testDeveloper.getTestDeveloperName()+"@"+testDeveloper.getNic()+"@"+testDeveloper.getTelephoneNo());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(testDeveloper.getTestDeveloperId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }     
    }
    
    public TestDeveloperDTO searchByIdTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        TestDeveloperDTO testDeveloper=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(testDeveloperId)) {
                    testDeveloper = new TestDeveloperDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));
                }
            }
            return testDeveloper;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public boolean isAvailable(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException{
        TestDeveloperDTO testDeveloper=searchByIdTestDeveloper(testDeveloperId);
        return testDeveloper==null;
    }
    
    public boolean modifyTestDeveloper(TestDeveloperDTO testDeveloper) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader bReader = null;
        BufferedWriter bWriter = null;

        try {
            lock.writeLock().lock();
            FileReader fileReader = new FileReader(file);
            bReader = new BufferedReader(fileReader);
            List<String> fileData = new ArrayList<>();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split("@");
                if (!data[0].equals(testDeveloper.getTestDeveloperId())) {
                    fileData.add(line);
                }else{
                    fileData.add(testDeveloper.getTestDeveloperId()+"@"+testDeveloper.getTestDeveloperName()+"@"+testDeveloper.getNic()+"@"+testDeveloper.getTelephoneNo());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(testDeveloper.getTestDeveloperId());
        } finally {
            if (bReader != null) {
                bReader.close();
            }
            if (bWriter != null) {
                bWriter.close();
            }
            lock.writeLock().unlock();
        }
    }
    
    public boolean removeTestDeveloper(String testDeveloperId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader bReader = null;
        BufferedWriter bWriter = null;

        try {
            lock.writeLock().lock();
            FileReader fileReader = new FileReader(file);
            bReader = new BufferedReader(fileReader);
            List<String> fileData = new ArrayList<>();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split("@");
                if (!data[0].equals(testDeveloperId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(testDeveloperId);
        } finally {
            if (bReader != null) {
                bReader.close();
            }
            if (bWriter != null) {
                bWriter.close();
            }
            lock.writeLock().unlock();
        }
    }
    
    public List<TestDeveloperDTO> getAllTestDeveloper() throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<TestDeveloperDTO> testDeveloperList = new ArrayList<>();
        try {
            lock.readLock().lock();
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    TestDeveloperDTO testDeveloper = new TestDeveloperDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));                    
                    testDeveloperList.add(testDeveloper);
                }
            }
            return testDeveloperList;
        } finally {
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
    
    public TestDeveloperDTO searchByNameTestDeveloper(String testDeveloperName) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        TestDeveloperDTO testDeveloper=null;
        int length=testDeveloperName.length();
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                String name=data[1].substring(0, length);
                if (name.equals(testDeveloperName)) {
                    testDeveloper = new TestDeveloperDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));
                }
            }
            return testDeveloper;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }     
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "t001";
        }
        BufferedReader reader = null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            String[] data = null;
            while ((line = reader.readLine()) != null) {
                data = line.split("@");
            }
            if (data==null) {
                return "t001";
            }
            String number=data[0].substring(1);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "t00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "t0"+nextNumber;
            }else {
                return "t"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
