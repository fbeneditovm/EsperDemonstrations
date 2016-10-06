/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esperdemonstrations;
import com.espertech.esper.client.*;
import java.util.Date;
import java.util.Random;
/**
 *
 * @author fbeneditovm
 */
public class EsperDemonstrations {
    
    private static Random generator = new Random();
    
    /**
     * Generate a new RadiationEvent and sends it
     */
    public static void generateRandomRadiation(EPRuntime cepRT){
        double radiation = (double) generator.nextInt(6) + generator.nextDouble();
        RadiationEvent rdEvent = new RadiationEvent(radiation, new Date());
        System.out.println("Sending radiation:" + rdEvent);
        cepRT.sendEvent(rdEvent);
    }
    /**
     * Generate a new TemperatureEvent and sends it
     */
    public static void generateRandomTemperature(EPRuntime cepRT){
        int temperature = generator.nextInt(500);
        TemperatureEvent tpEvent = new TemperatureEvent(temperature, new Date());
        System.out.println("Sending temperature:" + tpEvent);
        cepRT.sendEvent(tpEvent);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Configuration config = new Configuration();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
    }
    
}
