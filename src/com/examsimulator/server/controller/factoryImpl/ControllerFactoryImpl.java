/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.controller.factoryImpl;

import com.examsimulator.common.controller.AnswerController;
import com.examsimulator.common.controller.ExamController;
import com.examsimulator.common.controller.ExamDetailController;
import com.examsimulator.common.controller.MarksController;
import com.examsimulator.common.controller.QuestionController;
import com.examsimulator.common.controller.StudentController;
import com.examsimulator.common.controller.SubjectContoller;
import com.examsimulator.common.controller.SuperController;
import com.examsimulator.common.controller.TestDeveloperController;
import com.examsimulator.common.controller.factory.ControllerFactory;
import com.examsimulator.server.controller.AnswerControllerImpl;
import com.examsimulator.server.controller.ExamControllerImpl;
import com.examsimulator.server.controller.ExamDetailControllerImpl;
import com.examsimulator.server.controller.LoginControllerImpl;
import com.examsimulator.server.controller.MarksControllerImpl;
import com.examsimulator.server.controller.QuestionControllerImpl;
import com.examsimulator.server.controller.StudentControllerImpl;
import com.examsimulator.server.controller.SubjectControllerImpl;
import com.examsimulator.server.controller.TestDeveloperControllerImpl;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Lahiru Sandeepa
 */
public class ControllerFactoryImpl extends UnicastRemoteObject implements ControllerFactory{

    private static ControllerFactory controllerFactory;
    
    private ControllerFactoryImpl()throws RemoteException{
        
    }
    
//    @Override
//    public AnswerController getAnswerController() throws RemoteException {
//        return new AnswerControllerImpl();
//    }
//
//    @Override
//    public ExamController getExamController() throws RemoteException {
//        return new ExamControllerImpl();
//    }
//
//    @Override
//    public ExamDetailController getExamDetailController() throws RemoteException {
//        return new ExamDetailControllerImpl();
//    }
//
//    @Override
//    public MarksController getMarksController() throws RemoteException {
//        return new MarksControllerImpl();
//    }
//
//    @Override
//    public QuestionController getQuestionController() throws RemoteException {
//        return new QuestionControllerImpl();
//    }
//
//    @Override
//    public StudentController getStudentController() throws RemoteException {
//        return new StudentControllerImpl();
//    }
//
//    @Override
//    public SubjectContoller getSubjectContoller() throws RemoteException {
//        return new SubjectControllerImpl();
//    }
//
//    @Override
//    public TestDeveloperController getTestDeveloperController() throws RemoteException {
//        return new TestDeveloperControllerImpl();
//    }

    
    public static ControllerFactory getControllerFactory() throws RemoteException{
        if (controllerFactory==null) {
            controllerFactory=new ControllerFactoryImpl();
        }
        return controllerFactory;
    }
    
    @Override
    public SuperController getController(ControllerTypes controller) throws RemoteException {
        switch(controller){
            case ANSWER:
                return new AnswerControllerImpl();
            case EXAM:
                return new ExamControllerImpl();
            case EXAMDETAIL:
                return new ExamDetailControllerImpl();
            case MARKS:
                return new MarksControllerImpl();
            case QUESTION:
                return new QuestionControllerImpl();
            case STUDENT:
                return new StudentControllerImpl();
            case SUBJECT:
                return new SubjectControllerImpl();
            case TESTDEVELOPER:
                return new TestDeveloperControllerImpl();
            case LOGIN:
                return new LoginControllerImpl();
            default:
                return null;
        }
    }
    
}
