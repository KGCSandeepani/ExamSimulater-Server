/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.MarksDTO;
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
public class MarksFileAccess extends SuperFileAccess<MarksDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Marks.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addMarks(MarksDTO marks) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(marks.getMarksId()+"@"+marks.getExamId()+"@"+marks.getMarks());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(marks.getExamId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }    
    }
    public boolean isAvailable(String examId) throws RemoteException, FileNotFoundException, IOException{
        MarksDTO marks=searchMarks(examId);
        return marks==null;
    }
    public boolean modifyMarks(MarksDTO marks) throws RemoteException, IOException{
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
                if (!data[1].equals(marks.getExamId())) {
                    fileData.add(line);
                }else{
                    fileData.add(marks.getMarksId()+"@"+marks.getExamId()+"@"+marks.getMarks());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(marks.getExamId());
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
    
    public boolean removeMarks(String examId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[1].equals(examId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(examId);
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
    
    public MarksDTO searchMarks(String examId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        MarksDTO marks=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[1].equals(examId)) {
                    marks = new MarksDTO(data[0], data[1], Integer.parseInt(data[2]));
                }
            }
            return marks;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }      
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "m001";
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
                return "m001";
            }
            String number=data[0].substring(1);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "m00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "m0"+nextNumber;
            }else {
                return "m"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
