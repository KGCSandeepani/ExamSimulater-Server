/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.LoginDTO;
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
public class LoginFileAccess extends SuperFileAccess<LoginDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Login.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addLogin(LoginDTO loginDTO) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(loginDTO.getLoginId()+"@"+loginDTO.getUser()+"@"+loginDTO.getPassword());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(loginDTO.getLoginId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }     
    }

    private boolean isAvailable(String loginId) throws RemoteException, FileNotFoundException, IOException{
        LoginDTO loginDTO=searchLoginById(loginId);
        return loginDTO==null;
    }
    
    public LoginDTO searchLoginById(String loginId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        LoginDTO loginDTO=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(loginId)) {
                    loginDTO = new LoginDTO(data[0], data[1], data[2]);
                }
            }
            return loginDTO;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    public LoginDTO searchLogin(String userName, String password) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        LoginDTO loginDTO=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[1].equals(userName) && data[2].equals(password)) {
                    loginDTO = new LoginDTO(data[0], data[1], data[2]);
                }
            }
            return loginDTO;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public boolean removeLogin(String loginId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[0].equals(loginId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(loginId);
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
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "l001";
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
                return "l001";
            }
            String number=data[0].substring(1);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "l00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "l0"+nextNumber;
            }else {
                return "l"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
