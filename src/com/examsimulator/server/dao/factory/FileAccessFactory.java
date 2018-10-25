/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.dao.factory;

import com.examsimulator.server.dao.SuperFileAccess;

/**
 *
 * @author Lahiru Sandeepa
 */
public interface FileAccessFactory {

    public enum FileAccessTypes{
        ANSWER, EXAM, EXAMDETAIL, MARKS, QUESTION, STUDENT, SUBJECT, TESTDEVELOPER, LOGIN;
    }
    
    public SuperFileAccess getFileAccess(FileAccessTypes type);
    
//    public AnswerFileAccess getAnswerFileAccess();
//
//    public ExamDetailFileAccess getExamDetailFileAccess();
//
//    public ExamFileAccess getExamFileAccess();
//
//    public MarksFileAccess getMarksFileAccess();
//
//    public QuestionFileAccess getQuestionFileAccess();
//
//    public StudentFileAccess getStudentFileAccess();
//
//    public SubjectFileAccess getSubjectFileAccess();
//
//    public TestDeveloperFileAccess getTestDeveloperFileAccess();
}
