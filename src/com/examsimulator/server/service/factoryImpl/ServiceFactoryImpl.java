/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.service.factoryImpl;

import com.examsimulator.server.service.AnswerService;
import com.examsimulator.server.service.ExamDetailService;
import com.examsimulator.server.service.ExamService;
import com.examsimulator.server.service.LoginService;
import com.examsimulator.server.service.MarksService;
import com.examsimulator.server.service.QuestionService;
import com.examsimulator.server.service.StudentService;
import com.examsimulator.server.service.SubjectService;
import com.examsimulator.server.service.SuperService;
import com.examsimulator.server.service.TestDeveloperService;
import com.examsimulator.server.service.factory.ServiceFactory;

/**
 *
 * @author Lahiru Sandeepa
 */
public class ServiceFactoryImpl implements ServiceFactory{

    private static ServiceFactory serviceFactory;
    
    private ServiceFactoryImpl(){
        
    }
    
    @Override
    public SuperService getSevice(ServiceTypes type) {
        switch(type){
            case ANSWER:
                return new AnswerService();
            case EXAM:
                return new ExamService();
            case EXAMDETAIL:
                return new ExamDetailService();
            case MARKS:
                return new MarksService();
            case QUESTION:
                return new QuestionService();
            case STUDENT:
                return new StudentService();
            case SUBJECT:
                return new SubjectService();
            case TESTDEVELOPER:
                return new TestDeveloperService();
            case LOGIN:
                return new LoginService();
            default:
                return null;
        }
    }

    public static ServiceFactory getServiceFactory(){
        if (serviceFactory==null) {
            serviceFactory=new ServiceFactoryImpl();
        }
        return serviceFactory;
    }
//    @Override
//    public AnswerService getAnswerService() {
//        return new AnswerService();
//    }
//
//    @Override
//    public ExamDetailService getExamDetailService() {
//        return new ExamDetailService();
//    }
//
//    @Override
//    public ExamService getExamService() {
//        return new ExamService();
//    }
//
//    @Override
//    public MarksService getmMarksService() {
//        return new MarksService();
//    }
//
//    @Override
//    public QuestionService getQuestionService() {
//        return new QuestionService();
//    }
//
//    @Override
//    public StudentService getStudentService() {
//        return new StudentService();
//    }
//
//    @Override
//    public SubjectService getSubjectService() {
//        return new SubjectService();
//    }
//
//    @Override
//    public TestDeveloperService getTestDeveloperService() {
//        return new TestDeveloperService();
//    }
    
}
