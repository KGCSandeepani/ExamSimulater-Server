/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.StudentDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Lahiru Sandeepa
 */
public class StudentFileAccess extends SuperFileAccess<StudentDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Student.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addStudent(StudentDTO student)throws RemoteException,IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(student.getStudentId()+"@"+student.getStudentName()+"@"+student.getNic()+"@"+student.getTelephoneNo());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(student.getStudentId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }        
    }
    
    public StudentDTO searchByIdStudent(String studentId)throws RemoteException,FileNotFoundException, IOException{
        BufferedReader reader = null;
        StudentDTO student=null;
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(studentId)) {
                    student = new StudentDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));
                }
            }
            return student;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public boolean isAvailable(String studentId) throws RemoteException, FileNotFoundException, IOException{
        StudentDTO student=searchByIdStudent(studentId);
        return student==null;
    }
    
    public boolean modifyStudent(StudentDTO student)throws RemoteException,FileNotFoundException, IOException{
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
                if (!data[0].equals(student.getStudentId())) {
                    fileData.add(line);
                }else{
                    fileData.add(student.getStudentId()+"@"+student.getStudentName()+"@"+student.getNic()+"@"+student.getTelephoneNo());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(student.getStudentId());
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
    public boolean removeStudent(String studentId) throws RemoteException, FileNotFoundException, IOException{
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
                if (!data[0].equals(studentId)) {
                    fileData.add(line);
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(studentId);
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
    
    public List<StudentDTO> getAllStudent() throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<StudentDTO> studentList = new ArrayList<>();

        try {
            lock.readLock().lock();
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    StudentDTO student = new StudentDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));                    
                    studentList.add(student);
                }
            }
            return studentList;
        } finally {
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
    public StudentDTO searchByNameStudent(String studentName) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        StudentDTO student=null;
        int length=studentName.length();
        try{
            lock.readLock().lock();
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                String name=data[1].substring(0, length);
                if (name.equals(studentName)) {
                    student = new StudentDTO(data[0], data[1], data[2], Integer.parseInt(data[3]));
                }
            }
            return student;            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }        
    }
    
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "s001";
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
                return "s001";
            }
            String number=data[0].substring(1);
            int index=Integer.parseInt(number);
            int nextNumber=index+1;
            if (nextNumber<10) {
                return "s00"+nextNumber;
            }else if (nextNumber>=10 & nextNumber<100) {
                return "s0"+nextNumber;
            }else {
                return "s"+nextNumber;
            }            
        }finally{
            if (reader != null) {
                reader.close();
            }
            lock.readLock().unlock();
        }
    }
}
