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
    
    private static final String CRITICAL_EVENT_THRESHOLD = "100";
    private static final String CRITICAL_EVENT_MULTIPLIER = "1.5";
    private static final String WARNING_EVENT_THRESHOLD = "400";
    
    public static String createCtx20secAfterTemperature(){
        return "create context Ctx20secAfterTemperature" +
            "  initiated by TemperatureEvent(temperature>300) as tp" +
            "  terminated after 20 seconds";
    }
    
    public static String getLast5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length(5)";
    }
    
    public static String getBatch5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length_batch(5)";
    }
    
    public static String getLast5Temperature(){
        return "select irstream * from " +
               "TemperatureEvent.win:length(5)";
    }
    
    public static String getBatch5Temperature(){
        return "select irstream * from " +
               "TemperatureEvent.win:length_batch(5)";
    }
    
    public static String criticalRadiation(){
        return "select avg(radiation) as avgRd, timeOfReading "
                + "from RadiationEvent.win:time(10 sec) "
                + "where radiation>5 "
                + "having count(*)>2";
    }
    
    public static String warningRadiation(){
        return "select * from "
                + "RadiationEvent.win:length_batch(5) "
                + "where radiation>4";
    }
    
    public static String criticalTemperatureRadiation(){
        return "context Ctx20secAfterTemperature "
                + "select context.tp.temperature as temp, rd.radiation as rad, context.startTime as timeMillisec from "
                + "RadiationEvent as rd "
                + "where context.tp.temperature>400 and rd.radiation>4";
    }
    
    public static String warningTemperatureRadiation(){
        return "context Ctx20secAfterTemperature "
                + "select context.tp.temperature as temp, rd.radiation as rad, context.startTime as timeMillisec from "
                + "RadiationEvent as rd "
                + "where rd.radiation>3.5 and "
                + "not(context.tp.temperature>400 and rd.radiation>4)";
    }
}
