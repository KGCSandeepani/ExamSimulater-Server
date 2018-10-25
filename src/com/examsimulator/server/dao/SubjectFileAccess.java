/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.SubjectDTO;
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
public class SubjectFileAccess extends SuperFileAccess<SubjectDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Subject.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addSubject(SubjectDTO subject)throws RemoteException,IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(subject.getSubjectId()+"@"+subject.getSubjectName());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(subject.getSubjectId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }        
    }
    
    public SubjectDTO searchByIdSubject(String subjectId)throws RemoteException,FileNotFoundException, IOException{
        BufferedReader reader = null;
        SubjectDTO subject=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(subjectId)) {
                    subject = new SubjectDTO(data[0], data[1]);
                }
            }
            return subject;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public boolean isAvailable(String subjectId) throws RemoteException, FileNotFoundException, IOException{
        SubjectDTO subject=searchByIdSubject(subjectId);
        return subject==null;
    }
    
    public boolean modifySubject(SubjectDTO subject)throws RemoteException,FileNotFoundException, IOException{
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
                if (!data[0].equals(subject.getSubjectId())) {
                    fileData.add(line);
                }else{
                    fileData.add(subject.getSubjectId()+"@"+subject.getSubjectName());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(subject.getSubjectId());
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
    public boolean removeSubject(String subjectId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[0].equals(subjectId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(subjectId);
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
    
    public List<SubjectDTO> getAllSubject() throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<SubjectDTO> subjectList = new ArrayList<>();

        try {
            lock.readLock().lock();
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    SubjectDTO student = new SubjectDTO(data[0], data[1]);                    
                    subjectList.add(student);
                }
            }
            return subjectList;
        } finally {
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
    public SubjectDTO searchByNameSubject(String subjectName) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        SubjectDTO subject=null;
        int lenght=subjectName.length();
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                String name=data[1].substring(0,lenght);
              if (name.equals(subjectName)) {
                    subject = new SubjectDTO(data[0], data[1]);
                }
            }
            return subject;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "su001";
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
                return "su001";
            }
            String number=data[0].substring(2);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "su00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "su0"+nextNumber;
            }else {
                return "su"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
