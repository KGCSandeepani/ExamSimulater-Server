/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.main;

import com.examsimulator.server.controller.factoryImpl.ControllerFactoryImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lahiru Sandeepa
 */
public class ServerStart {
    public static void main(String[] args) {
        try {
            Registry registry=LocateRegistry.createRegistry(5050);
            System.out.println("Server is starting");
            registry.rebind("ExamSimulatorServer", ControllerFactoryImpl.getControllerFactory());
        } catch (RemoteException ex) {
            Logger.getLogger(ServerStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
