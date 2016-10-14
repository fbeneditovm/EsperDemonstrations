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
        return "create context CtxTrainEnter" +
            "  initiated by TemperatureEvent as tp" +
            "  terminated after 20 minutes";
    }
    
    public static String getLast5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length(5)";
    }
    
    public static String getBatch5Radiation(){
        return "select irstream * from " +
               "RadiationEvent.win:length_batch(5)";
    }
    
    public static String criticalTemperature(){
        return "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2, C as temp3, D as temp4 "
                + "       pattern (A B C D) " 
                + "       define "
                + "               A as A.temperature > " + CRITICAL_EVENT_THRESHOLD + ", "
                + "               B as (A.temperature < B.temperature), "
                + "               C as (B.temperature < C.temperature), "
                + "               D as (C.temperature < D.temperature) and D.temperature > (A.temperature * " + CRITICAL_EVENT_MULTIPLIER + ")" + ")";
    }
    
    public static String warningTemperature(){
        return "select * from TemperatureEvent "
                + "match_recognize ( "
                + "       measures A as temp1, B as temp2 "
                + "       pattern (A B) " 
                + "       define " 
                + "               A as A.temperature > " + WARNING_EVENT_THRESHOLD + ", "
                + "               B as B.temperature > " + WARNING_EVENT_THRESHOLD + ")";
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
    
    public static String criticalTemperaruteRadiation(){
        return "context Ctx20secAfterTemperature"
                + "select context.tp.temperature, rd.radiation, context.startTime as timeMillisec from "
                + "RadiationEvent as rd"
                + "where context.tp.temperature>400 and rd.radiation>4 ";
    }
    
    public static String warningTemperatureRadiation(){
        return "context Ctx20secAfterTemperature "
                + "select context.tp.temperature, rd.radiation, context.startTime as timeMillisec from "
                + "RadiationEvent as rd"
                + "where context.tp.temperature>300 and rd.radiation>3.5";
    }
}
