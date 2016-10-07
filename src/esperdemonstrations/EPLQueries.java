/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esperdemonstrations;

/**
 *
 * @author fbeneditovm
 */
public class EPLQueries {
    public static String getLast5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length(5)";
    }
    
    public static String getBatch5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length_batch(5)";
    }
}