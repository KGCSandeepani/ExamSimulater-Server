/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.AnswerDTO;
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
public class AnswerFileAccess extends SuperFileAccess<AnswerDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Answer.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addAnswer(AnswerDTO answer) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(answer.getAnswerId()+"@"+answer.getQuestionId()+"@"+answer.getAnswer()+"@"+answer.getMarks());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(answer.getQuestionId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }     
    }
    
    public AnswerDTO searchAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        AnswerDTO answer=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[1].equals(questionId)) {
                    answer = new AnswerDTO( data[0], data[1],data[2], Integer.parseInt(data[3]));
                }
            }
            return answer;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public boolean isAvailable(String questionId) throws RemoteException, FileNotFoundException, IOException{
        AnswerDTO answer=searchAnswer(questionId);
        return answer==null;
    }
    
    public boolean modifyTestDeveloper(AnswerDTO answer) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[1].equals(answer.getQuestionId())) {
                    fileData.add(line);
                }else{
                    fileData.add(answer.getAnswerId()+"@"+answer.getQuestionId()+"@"+answer.getAnswer()+"@"+answer.getMarks());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(answer.getQuestionId());
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
    
    public boolean removeAnswer(String questionId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[1].equals(questionId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(questionId);
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
            return "a001";
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
                return "a001";
            }
            String number=data[0].substring(1);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "a00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "a0"+nextNumber;
            }else {
                return "a"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
