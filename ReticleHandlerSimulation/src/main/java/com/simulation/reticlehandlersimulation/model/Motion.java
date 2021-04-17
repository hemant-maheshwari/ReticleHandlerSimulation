package com.simulation.reticlehandlersimulation.model;

import java.util.Date;

public class Motion {
    
    private String location;
    private Date startTime;
    private String motionCode;

    public Motion(String location, Date startTime, String motionCode) {
        this.location = location;
        this.startTime = startTime;
        this.motionCode = motionCode;
    }

    public String getMotionCode() {
        return motionCode;
    }

    public void setMotionCode(String motionCode) {
        this.motionCode = motionCode;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
}
