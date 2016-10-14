/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esperdemonstrations;

import GUI.EventLogScreen;
import GUI.WarningScreen;
import com.espertech.esper.client.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author fbeneditovm
 */

/**
 * Event Generator Class
 */
class Generator extends Thread{
    private static Random generator=new Random();
    private EPRuntime cepRT;
    
    Generator(EPRuntime cepRT){
        this.cepRT = cepRT;
    }
    
    /**
     * Generate a new RadiationEvent and sends it
     */
    public void generateRandomRadiation(){
        double radiation = (double) generator.nextInt(6) + generator.nextDouble();
        RadiationEvent rdEvent = new RadiationEvent(radiation, new Date());
        //System.out.println("Sending radiation:" + rdEvent);
        cepRT.sendEvent(rdEvent);
    }
    
    /**
     * Generate a new TemperatureEvent and sends it
     */
    public void generateRandomTemperature(){
        int temperature = generator.nextInt(500);
        TemperatureEvent tpEvent = new TemperatureEvent(temperature, new Date());
        //System.out.println("Sending temperature:" + tpEvent);
        cepRT.sendEvent(tpEvent);
    }
    
    @Override
    public void run(){
        while(true){
            generateRandomTemperature();
            generateRandomRadiation();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

/**
 * Update Listener that gets the last 5 RadiationEvents
 */
class RadiationWindowListener implements UpdateListener{
    
    EventLogScreen screen;
    LinkedList<String> inEvents;
    LinkedList<String> rmEvents;
    
    public void setScreen(EventLogScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        rmEvents = new LinkedList<>();
        
        System.out.println("Number of news"+newData.length);
        
        if(oldData == null)
            System.out.println("No old Events");
        else
            System.out.println("Number of olds"+oldData.length);
        
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("Radiation: "+new DecimalFormat("#.###").format((double)newData[i].get("radiation"))+" uSv "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        if(oldData != null){
            for(int i=0; i<oldData.length; i++){
                rmEvents.add("Radiation: "+new DecimalFormat("#.###").format((double)oldData[i].get("radiation"))+" uSv "+
                             "- at "+(Date)oldData[i].get("timeOfReading"));
            }
        }
        
        //Send Events to GUI
        screen.newEvents(inEvents, rmEvents);
    }
}

/**
 * Update Listener that gets the RadiationEvents 5 by 5
 */
class RadiationBatchListener implements UpdateListener{
    
    EventLogScreen screen;
    LinkedList<String> inEvents;
    LinkedList<String> rmEvents;
    
    public void setScreen(EventLogScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        rmEvents = new LinkedList<>();
        
        System.out.println("Number of news"+newData.length);
        
        if(oldData == null)
            System.out.println("No old Events");
        else
            System.out.println("Number of olds"+oldData.length);
        
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("Radiation: "+new DecimalFormat("#.###").format((double)newData[i].get("radiation"))+" uSv "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        if(oldData != null){
            for(int i=0; i<oldData.length; i++){
                rmEvents.add("Radiation: "+new DecimalFormat("#.###").format((double)oldData[i].get("radiation"))+" uSv "+
                             "- at "+(Date)oldData[i].get("timeOfReading"));
            }
        }
        
        //Send Events to GUI
        screen.newEvents(inEvents, rmEvents);
    }
}

class RadiationCriticalListener implements UpdateListener{
    
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news RdCritical "+newData.length);
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("CRITICAL: Average Radiation: "+new DecimalFormat("#.###").format((double)newData[i].get("avgRd"))+" uSv "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        
        //Send Events to GUI
        screen.newCritical(inEvents);
    }
}

class RadiationWarningListener implements UpdateListener{
    
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news RdWarning "+newData.length);
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("WARNING: Radiation levels are too high: "+new DecimalFormat("#.###").format((double)newData[i].get("radiation"))+" uSv "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        
        //Send Events to GUI
        screen.newWarning(inEvents);
    }
}

/**
 * Update Listener that gets the last 5 TemperatureEvents
 */
class TemperatureWindowListener implements UpdateListener{
    
    EventLogScreen screen;
    LinkedList<String> inEvents;
    LinkedList<String> rmEvents;
    
    public void setScreen(EventLogScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        rmEvents = new LinkedList<>();
        
        System.out.println("Number of news"+newData.length);
        
        if(oldData == null)
            System.out.println("No old Events");
        else
            System.out.println("Number of olds"+oldData.length);
        
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("Temperature: "+(int)newData[i].get("temperature")+"º C "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        if(oldData != null){
            for(int i=0; i<oldData.length; i++){
                rmEvents.add("Temperature: "+(int)oldData[i].get("temperature")+"º C "+
                             "- at "+(Date)oldData[i].get("timeOfReading"));
            }
        }
        
        //Send Events to GUI
        screen.newEvents(inEvents, rmEvents);
    }
}

/**
 * Update Listener that gets the TemperatureEvents 5 by 5
 */
class TemperatureBatchListener implements UpdateListener{
    
    EventLogScreen screen;
    LinkedList<String> inEvents;
    LinkedList<String> rmEvents;
    
    public void setScreen(EventLogScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        rmEvents = new LinkedList<>();
        
        System.out.println("Number of news"+newData.length);
        
        if(oldData == null)
            System.out.println("No old Events");
        else
            System.out.println("Number of olds"+oldData.length);
        
        
        //Store Events in Arrays
        for(int i=0; i<newData.length; i++){
            if(newData[i]==null){
                System.out.println("We got a null");
                break;
            }
            inEvents.add("Temperature: "+(int)newData[i].get("temperature")+"º C "+
                          "- at "+(Date)newData[i].get("timeOfReading"));
            System.out.println("Event received: "+ newData[i].getUnderlying());
        }
        if(oldData != null){
            for(int i=0; i<oldData.length; i++){
                rmEvents.add("Temperature: "+(int)oldData[i].get("temperature")+"º C "+
                             "- at "+(Date)oldData[i].get("timeOfReading"));
            }
        }
        
        //Send Events to GUI
        screen.newEvents(inEvents, rmEvents);
    }
}

class TemperatureCriticalListener implements UpdateListener{
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news TpWarning "+newData.length);
        
        inEvents.add("CRITICAL: Temperature is out of control: "+(int)newData[0].get("temperature")+
                          ", "+(int)newData[1].get("temperature")+", "+(int)newData[2].get("temperature")+ 
                          ", "+(int)newData[3].get("temperature")+"º C "+
                          "- at "+(Date)newData[0].get("timeOfReading"));
        System.out.println("Event received: "+ newData[0].getUnderlying());
        
        
        //Send Events to GUI
        screen.newCritical(inEvents);
    }
}

class TemperatureWarningListener implements UpdateListener{
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news TpWarning "+newData.length);
        
        inEvents.add("WARNING: Temperature Spike: "+(int)newData[0].get("temperature")+
                          " -> "+(int)newData[1].get("temperature")+" º C "+
                          "- at "+(Date)newData[0].get("timeOfReading"));
        System.out.println("Event received: "+ newData[0].getUnderlying());
        
        
        //Send Events to GUI
        screen.newWarning(inEvents);
    }
}

class TemperatureRadiationCriticalListener implements UpdateListener{
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news TpWarning "+newData.length);
        
        inEvents.add("CRITICAL: VERY High Temperature: "+(int)newData[0].get("temp")+" º C "+
                          " followed by VERY High Radiation: "+new DecimalFormat("#.###").format((double)newData[0].get("rad"))+" uSv "+
                          "- at "+new Date((long)newData[0].get("timeOfReading"));
        System.out.println("Event received: "+ newData[0].getUnderlying());
        
        
        //Send Events to GUI
        screen.newWarning(inEvents);
    }
}

class TemperatureRadiationWarningListener implements UpdateListener{
    WarningScreen screen;
    LinkedList<String> inEvents;
    
    public void setScreen(WarningScreen screen){
        this.screen = screen;
    }
    
    @Override
    public void update(EventBean[] newData, EventBean[] oldData) {
        inEvents = new LinkedList<>();
        
        System.out.println("Number of news TpWarning "+newData.length);
        
        inEvents.add("WARNING: High Temperature: "+(int)newData[0].get("temp")+" º C "+
                          " followed by High Radiation: "+new DecimalFormat("#.###").format((double)newData[0].get("rad"))+" uSv "+
                          "- at "+new Date((long)newData[0].get("timeOfReading"));
        System.out.println("Event received: "+ newData[0].getUnderlying());
        
        
        //Send Events to GUI
        screen.newWarning(inEvents);
    }
}

public class CEPHandler {
    
    EventLogScreen logScreen;
    WarningScreen warningScreen;
    
    RadiationWindowListener rwListener;
    RadiationBatchListener rbListener;
    
    EPStatement last5RadiationStatement;
    EPStatement batch5RadiationStatement;
    
    public CEPHandler(){
        logScreen = new EventLogScreen(this);
        warningScreen = new WarningScreen();
        rwListener = new RadiationWindowListener();
        rwListener.setScreen(logScreen);
        rbListener = new RadiationBatchListener();
        rbListener.setScreen(logScreen);
    }
    
    /**
     * Get the Events by Batch
     */
    public void getByBatch(){
        last5RadiationStatement.removeAllListeners();
        batch5RadiationStatement.addListener(rbListener);
        System.out.println("Changed to batch");
    }
    
    /**
     * Get the Events by Window
     */
    public void getByWindow(){
        batch5RadiationStatement.removeAllListeners();
        last5RadiationStatement.addListener(rwListener);
        System.out.println("Changed to window");
    }
    
    public void initService(){
        //Start GUI
        logScreen.setVisible(true);
        
        //Configuration
        Configuration config = new Configuration();
        
        //Events Register
        config.addEventType("RadiationEvent", RadiationEvent.class.getName());
        config.addEventType("TemperatureEvent", TemperatureEvent.class.getName());
        
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        EPAdministrator epAdm = epService.getEPAdministrator();
        //EPLStatement and Listener registration
        last5RadiationStatement = epAdm.createEPL(EPLQueries.getLast5Radiation());
        batch5RadiationStatement = epAdm.createEPL(EPLQueries.getBatch5Radiation());
        last5RadiationStatement.addListener(rwListener);
        
        // Event Generation
        EPRuntime cepRT = epService.getEPRuntime();
        Generator generator = new Generator(cepRT);
        generator.start();
    }
}
