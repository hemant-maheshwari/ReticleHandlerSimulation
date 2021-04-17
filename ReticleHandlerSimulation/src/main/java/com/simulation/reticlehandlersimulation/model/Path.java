package com.simulation.reticlehandlersimulation.model;

public class Path {

    private String toStation;
    private String fromStation;
    private long travelTime;
    private boolean error;

    public Path(String fromStation, String toStation, long travelTime) {
        this.toStation = toStation;
        this.fromStation = fromStation;
        this.travelTime = travelTime;
        this.error = false;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
    
}
