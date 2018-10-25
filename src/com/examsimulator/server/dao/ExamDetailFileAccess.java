/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.ExamDetailDTO;
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
public class ExamDetailFileAccess extends SuperFileAccess<ExamDetailDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/ExamDetail.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addExamDetail(ExamDetailDTO examDetail) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(examDetail.getExamDetailId()+"@"+examDetail.getExamId()+"@"+examDetail.getQuestionId());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(examDetail.getExamDetailId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }  
    }
    
    public boolean isAvailable(String examDetailId) throws RemoteException, FileNotFoundException, IOException{
        ExamDetailDTO examDetail=searchExamDetail(examDetailId);
        return examDetail==null;
    }
    
    public boolean removeExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[0].equals(examDetailId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(examDetailId);
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
    
    public ExamDetailDTO searchExamDetail(String examDetailId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        ExamDetailDTO examDetail=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(examDetailId)) {
                    examDetail = new ExamDetailDTO(data[0], data[1], data[2]);
                }
            }
            return examDetail;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }     
    }
    
    public List<String> getQuestionId(String examId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<String> questionIdList = new ArrayList<>();
        try {
            lock.readLock().lock();
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    if (data[1].equals(examId)) {
                        questionIdList.add(data[2]);
                    }                    
                }
            }
            return questionIdList;
        } finally {
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "ed001";
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
                return "ed001";
            }
            String number=data[0].substring(2);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "ed00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "ed0"+nextNumber;
            }else {
                return "ed"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
