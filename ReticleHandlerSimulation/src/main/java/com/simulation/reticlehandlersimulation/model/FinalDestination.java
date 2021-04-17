package com.simulation.reticlehandlersimulation.model;

public class FinalDestination {

    private String reticleName;
    private String finalStation;

    public FinalDestination(String reticleName, String finalStation) {
        this.reticleName = reticleName;
        this.finalStation = finalStation;
    }
    
    public String getReticleName() {
        return reticleName;
    }

    public void setReticleName(String reticleName) {
        this.reticleName = reticleName;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }
    
    
    
}
