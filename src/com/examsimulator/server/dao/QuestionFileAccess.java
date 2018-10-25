/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao;

import com.examsimulator.common.dto.QuestionDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author Lahiru Sandeepa
 */
public class QuestionFileAccess extends SuperFileAccess<QuestionDTO>{
    private static final File file = new File("./src/com/examsimulator/server/files/Question.txt");
    private static ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    
    public boolean addQuestion(QuestionDTO question) throws RemoteException, IOException{
        if (!file.exists()) {
            boolean createNewFile = file.createNewFile();
        }
        BufferedWriter bufferedWriter = null;
        try{
            lock.writeLock().lock();
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            bufferedWriter.write(question.getQuestionId()+"@"+question.getSubjectId()+"@"+question.getTestDeveloperId()+"@"+question.getQuestion()+"@"+question.getOptionOne()+"@"+question.getOptionTwo()+"@"+question.getOptionThree()+"@"+question.getOptionFour()+"@"+question.getTime());
            bufferedWriter.newLine();
            boolean isAvailable=isAvailable(question.getQuestionId());        
            return isAvailable;
        }finally{
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            lock.writeLock().unlock();
        }    
    }
    
    public boolean isAvailable(String questionId) throws RemoteException, FileNotFoundException, IOException{
        QuestionDTO question=searchQuestion(questionId);
        return question==null;
    }
    
    public boolean modifyQuestion(QuestionDTO question) throws RemoteException, IOException{
        BufferedReader bReader = null;
        BufferedWriter bWriter = null;
        System.out.println("1");
        try {
            lock.writeLock().lock();
            FileReader fileReader = new FileReader(file);
            bReader = new BufferedReader(fileReader);
            List<String> fileData = new ArrayList<>();
            String line = null;
            System.out.println("2");
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split("@");
                if (!data[0].equals(question.getQuestionId())) {
                    fileData.add(line);
                    System.out.println(question.getQuestionId());
                }else{
                    System.out.println(question.getQuestionId());
                    fileData.add(question.getQuestionId()+"@"+question.getSubjectId()+"@"+question.getTestDeveloperId()+"@"+question.getQuestion()+"@"+question.getOptionOne()+"@"+question.getOptionTwo()+"@"+question.getOptionThree()+"@"+question.getOptionFour()+"@"+question.getTime());
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            bWriter = new BufferedWriter(fileWriter);
            for (String lineData : fileData) {
                bWriter.write(lineData);
                bWriter.newLine();
            }
            return isAvailable(question.getQuestionId());
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
    
    public boolean removeQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader bReader = null;
        BufferedWriter bWriter = null;
        try {
            FileReader fileReader = new FileReader(file);
            bReader = new BufferedReader(fileReader);
            List<String> fileData = new ArrayList<>();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split("@");
                if (!data[0].equals(questionId)) {
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
        }
    }
    
    public QuestionDTO searchQuestion(String questionId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        QuestionDTO question=null;
        try{
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("@");
                if (data[0].equals(questionId)) {
                    question = new QuestionDTO(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], Integer.parseInt(data[8]));
                }
            }
            return question;            
        }finally{
            if (reader != null) {
                reader.close();
            }
        } 
    }
    
    public List<QuestionDTO> getAllQuestion() throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<QuestionDTO> questionList = new ArrayList<>();
        try {
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    QuestionDTO question = new QuestionDTO(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], Integer.parseInt(data[8]));                    
                    questionList.add(question);
                }
            }
            return questionList;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    public List<QuestionDTO> getSelectedQuestion(String subjectId,String testDeveloperId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<QuestionDTO> questionList = new ArrayList<>();        
        try {
            if (file.exists()) {                
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");                    
                    if (data[1].equals(subjectId) && data[2].equals(testDeveloperId)) {                        
                        QuestionDTO question = new QuestionDTO(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], Integer.parseInt(data[8]));                    
                        questionList.add(question);
                    }                    
                }
            }
            return questionList;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    public List<QuestionDTO> getQuestion(String subjectId) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;
        List<QuestionDTO> allQuestionList = new ArrayList<>();
        List<QuestionDTO> questionList = new ArrayList<>();
        try {
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                int index=0;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    if (data[1].equals(subjectId)) {
                        QuestionDTO question = new QuestionDTO(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], Integer.parseInt(data[8]));                    
                        allQuestionList.add(question);   
                        index+=1;
                    }                    
                }
                HashSet<Integer> numbers=new HashSet<>();
                Random random=new Random();
                int n=10;
                if (index<10) {
                    n=index;
                } 
                System.out.println("n : "+n);
                while (n>0) {                                        
                    int lineNumber=random.nextInt(index)+1;                    
                    if (numbers.add(lineNumber)) {                        
                        QuestionDTO question = allQuestionList.get(lineNumber-1);
                        questionList.add(question);   
                        n-=1;
                    }
                }
            }
            return questionList;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }    
    
    public int calculateTime(List<String> questionIdList) throws RemoteException, FileNotFoundException, IOException{
        BufferedReader reader = null;        
        int time=0;
        try {
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("@");
                    if (questionIdList.contains(data[0])) {
                        time+=Integer.parseInt(data[8]);
                    }                    
                }
            }
            return time;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
    public String generateId()throws RemoteException, FileNotFoundException, IOException{
        if (!file.exists()) {
            return "1";
        }
        BufferedReader reader = null;
        try{
            FileReader fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            String[] data = null;
            while ((line = reader.readLine()) != null) {
                data = line.split("@");
            }
            if (data==null) {
                return "1";
            }
            int index=Integer.parseInt(data[0]);
            return Integer.toString(index+1);
        }finally{
            if (reader != null) {
                reader.close();
            }
        }
    }
}
