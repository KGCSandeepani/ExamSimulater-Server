/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.examsimulator.server.reservation;

import com.examsimulator.common.controller.SuperController;
import java.util.HashMap;

/**
 *
 * @author Lahiru Sandeepa
 */
public class Reserver {
    private HashMap<String, SuperController> reserveData = new HashMap<>();
    
    public boolean reserve(String id,SuperController superController){
        if (reserveData.containsKey(id)) {
            return reserveData.get(id)==superController;
        } else {
            reserveData.put(id, superController);
            return true;
        }
    }
    
    public boolean release(String id,SuperController superController){
        if (reserveData.get(id)==superController) {
            reserveData.remove(id);
            return true;
        } else {
            return false;
        }
    }
    
}
