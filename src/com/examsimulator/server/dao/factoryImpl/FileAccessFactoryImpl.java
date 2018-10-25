/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao.factoryImpl;

import com.examsimulator.server.dao.AnswerFileAccess;
import com.examsimulator.server.dao.ExamDetailFileAccess;
import com.examsimulator.server.dao.ExamFileAccess;
import com.examsimulator.server.dao.LoginFileAccess;
import com.examsimulator.server.dao.MarksFileAccess;
import com.examsimulator.server.dao.QuestionFileAccess;
import com.examsimulator.server.dao.StudentFileAccess;
import com.examsimulator.server.dao.SubjectFileAccess;
import com.examsimulator.server.dao.SuperFileAccess;
import com.examsimulator.server.dao.TestDeveloperFileAccess;
import com.examsimulator.server.dao.factory.FileAccessFactory;

/**
 *
 * @author Lahiru Sandeepa
 */
public class FileAccessFactoryImpl implements FileAccessFactory{

    private static FileAccessFactory fileAccessFactory;
    
    private FileAccessFactoryImpl(){
        
    }
//    @Override
//    public AnswerFileAccess getAnswerFileAccess() {
//        return new AnswerFileAccess();
//    }
//
//    @Override
//    public ExamDetailFileAccess getExamDetailFileAccess() {
//        return new ExamDetailFileAccess();
//    }
//
//    @Override
//    public ExamFileAccess getExamFileAccess() {
//        return new ExamFileAccess();
//    }
//
//    @Override
//    public MarksFileAccess getMarksFileAccess() {
//        return new MarksFileAccess();
//    }
//
//    @Override
//    public QuestionFileAccess getQuestionFileAccess() {
//        return new QuestionFileAccess();
//    }
//
//    @Override
//    public StudentFileAccess getStudentFileAccess() {
//        return new StudentFileAccess();
//    }
//
//    @Override
//    public SubjectFileAccess getSubjectFileAccess() {
//        return new SubjectFileAccess();
//    }
//
//    @Override
//    public TestDeveloperFileAccess getTestDeveloperFileAccess() {
//        return new TestDeveloperFileAccess();
//    }

    @Override
    public SuperFileAccess getFileAccess(FileAccessTypes type) {
        switch(type){
            case ANSWER:
                return new AnswerFileAccess();
            case EXAM:
                return new ExamFileAccess();
            case EXAMDETAIL:
                return new ExamDetailFileAccess();
            case MARKS:
                return new MarksFileAccess();
            case QUESTION:
                return new QuestionFileAccess();
            case STUDENT:
                return new StudentFileAccess();
            case SUBJECT:
                return new SubjectFileAccess();
            case TESTDEVELOPER:
                return new TestDeveloperFileAccess();
            case LOGIN:
                return new LoginFileAccess();
            default:
                return null;
        }
    }
    public static FileAccessFactory getFileAccess(){
        if (fileAccessFactory==null) {
            fileAccessFactory=new FileAccessFactoryImpl();
        }
        return fileAccessFactory;
    }
}
