/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service.factory;

import com.examsimulator.server.service.AnswerService;
import com.examsimulator.server.service.ExamDetailService;
import com.examsimulator.server.service.ExamService;
import com.examsimulator.server.service.MarksService;
import com.examsimulator.server.service.QuestionService;
import com.examsimulator.server.service.StudentService;
import com.examsimulator.server.service.SubjectService;
import com.examsimulator.server.service.SuperService;
import com.examsimulator.server.service.TestDeveloperService;

/**
 *
 * @author Lahiru Sandeepa
 */
public interface ServiceFactory {

    public enum ServiceTypes{
        ANSWER, EXAM, EXAMDETAIL, MARKS, QUESTION, STUDENT, SUBJECT, TESTDEVELOPER, LOGIN;
    }
    
    public SuperService getSevice(ServiceTypes type);
    
//    public AnswerService getAnswerService();
//
//    public ExamDetailService getExamDetailService();
//
//    public ExamService getExamService();
//
//    public MarksService getmMarksService();
//
//    public QuestionService getQuestionService();
//
//    public StudentService getStudentService();
//
//    public SubjectService getSubjectService();
//
//    public TestDeveloperService getTestDeveloperService();
}
