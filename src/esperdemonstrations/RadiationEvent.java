/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esperdemonstrations;

import java.util.Date;

/**
 *
 * @author fbeneditovm
 */
public class RadiationEvent {
    /** Radiation in uSv/h. */
    private double radiation;
    
    /** Time radiation reading was taken. */
    private Date timeOfReading;
    
    
    /**
     * Radiation constructor.
     * @param radiation Temperature in Celsius
     * @param timeOfReading Time of Reading
     */
    public RadiationEvent(double radiation, Date timeOfReading) {
        this.radiation = radiation;
        this.timeOfReading = timeOfReading;
    }

    /**
     * Get the Radiation.
     * @return Radiation in uSv/h
     */
    public double getRadiation() {
        return radiation;
    }
       
    /**
     * Get time Radiation reading was taken.
     * @return Time of Reading
     */
    public Date getTimeOfReading() {
        return timeOfReading;
    }

    @Override
    public String toString() {
        return "RadiationEvent [" + radiation + "uSv/h]";
    }
}
