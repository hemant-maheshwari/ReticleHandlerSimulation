package com.simulation.reticlehandlersimulation.service;

import com.simulation.reticlehandlersimulation.constant.LogEntryType;
import com.simulation.reticlehandlersimulation.model.FinalDestination;
import com.simulation.reticlehandlersimulation.model.LogObject;
import com.simulation.reticlehandlersimulation.model.Motion;
import com.simulation.reticlehandlersimulation.model.Path;
import com.simulation.reticlehandlersimulation.model.Reticle;
import com.simulation.reticlehandlersimulation.util.LogReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class ReticleHandlerService {

    private final LogHandlerService logHandlerService;
    private final LogReader logReader;
    private final String[] logData;
    private final String REGEX = "'";
    private final String MOTION_CODE_FROM = "1235";
    private final String MOTION_CODE_TO = "1234";
    private final String DESTINATION_LOCATION_CODE = "DestinationLocation";
    //private final String MOTION_CODE = "1235";
    private List<Reticle> reticles;
    private List<FinalDestination> finalDestinations;
    
    public ReticleHandlerService(File logFile) {
        logHandlerService = new LogHandlerService();
        logReader = new LogReader(logFile);
        logData = logReader.readFile();
        reticles = new ArrayList<>();
        finalDestinations = new ArrayList<>();
    }
    
    public List<Reticle> getReticles(){
        getReticlesFromLogObject();
        updateReticlePaths();
        printAllReticlePaths();
        return reticles;
    }
    
    private void printAllReticlePaths(){
        for(Reticle reticle: reticles){
            System.out.println("Reticle: "+ reticle.getName());
            for(Path path: reticle.getPaths()){
                System.out.println("Path:\nFrom: "+ path.getFromStation()+ "\nTo: "+path.getToStation()+"\nTime: "+path.getTravelTime()+"\nError: "+path.isError());
                System.out.println("----------------------------------");
            }
            System.out.println("==================================");
        }
    }
    
    public List<FinalDestination> getFinalDestinations(){
        return finalDestinations;
    }
    
    private void getReticlesFromLogObject(){
        try{
            List<LogObject> logObjects = logHandlerService.getLogObjectFromLogData(logData);
            for(LogObject logObject: logObjects){
                if(logObject.getObjectType().equals(LogEntryType.SdrRequest.name())){
                    if(containsMotionCodeFrom(logObject.getData())){
                        updateReticles(logObject, MOTION_CODE_FROM);
                    }
                    if(containsMotionCodeTo(logObject.getData())){
                        updateReticles(logObject, MOTION_CODE_TO);
                    }
                }
                if(logObject.getObjectType().equals(LogEntryType.SdrMessageGet.name())){
                    if(containsDestinationCode(logObject.getData())){
                        updateFinalDestination(logObject);
                    }
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error reading log file!\nPlease choose correct log file.");
        }
    }
    
    private boolean containsDestinationCode(String logEntryString){
        return logEntryString.contains(DESTINATION_LOCATION_CODE);
    }
    
    private boolean containsMotionCodeFrom(String logEntryString){
        return logEntryString.contains(MOTION_CODE_FROM);
    }
    
    private boolean containsMotionCodeTo(String logEntryString){
        return logEntryString.contains(MOTION_CODE_TO);
    }
    
    private void updateFinalDestination(LogObject logObject){
        String[] reticleData = parseReticleDataFromEntryLog(logObject.getData());
        String reticleName = reticleData[7];
        String stationName = reticleData[11];
        addUpdateDestination(reticleName, stationName);
    }
    
    private void updateReticles(LogObject logObject, String motionCode){
        String[] reticleData = parseReticleDataFromEntryLog(logObject.getData());
        String reticleName = reticleData[1];
        String stationName = reticleData[3];
        addUpdateReticle(reticleName, stationName, logObject.getObjectTime(), motionCode);
    }
    
    private void addUpdateDestination(String reticleName, String stationName){
        FinalDestination finalDestination;
        if(findFinalDestination(reticleName)==-1){
            finalDestination = new FinalDestination(reticleName, stationName);
            finalDestinations.add(finalDestination);
        }
        else{
            int finalDestinationIndex = findFinalDestination(reticleName);
            finalDestinations.get(finalDestinationIndex).setFinalStation(stationName);
        }
    }
    
    private int findFinalDestination(String reticleName){
        for(int i=0; i<finalDestinations.size(); i++){
            if(finalDestinations.get(i).getReticleName().equals(reticleName)){
                return i;
            }
        }
        return -1;
    }
    
    private void addUpdateReticle(String reticleName, String stationName, Date motionTime, String motionCode){
        Reticle reticle;
        if(findReticleIndex(reticleName)==-1){
            reticle = new Reticle(reticleName);
            reticles.add(reticle);
        }
        else{
            int reticleIndex = findReticleIndex(reticleName);
            reticle = reticles.get(reticleIndex);
        }
        Motion motion = new Motion(stationName, motionTime, motionCode);
        reticle.getMotions().add(motion);
    }
    
    private int findReticleIndex(String reticleName){
        for(int i=0; i<reticles.size(); i++){
            if(reticles.get(i).getName().equals(reticleName)){
                return i;
            }
        }
        return -1;
    }
    
    private String[] parseReticleDataFromEntryLog(String reticleData){
        String[] strArr = reticleData.split(REGEX);
        return strArr;
    }
    
    private void updateReticlePaths(){
        for(Reticle reticle: reticles){
            updatePath(reticle);
        }
    }
    
    private void updatePath(Reticle reticle){
        List<Path> paths = new ArrayList<>();
        List<Motion> motions = reticle.getMotions();
        if(motions.size()>0){
            for(int i=0; i<motions.size()-1; i++){
                Path path = new Path(motions.get(i).getLocation(), motions.get(i+1).getLocation(), getTimeDiff(motions.get(i+1).getStartTime(), motions.get(i).getStartTime()));
                paths.add(path);
            }
        }
        finalizePaths(paths);
        reticle.setPaths(paths);
    }
    
    private void finalizePaths(List<Path> paths){
        try{
            for(int i=1; i<paths.size(); i++){
                if(paths.get(i).getFromStation().equals(paths.get(i).getToStation()) && paths.get(i).getFromStation().equals(paths.get(i-1).getToStation())){
                    paths.get(i-1).setTravelTime(paths.get(i).getTravelTime());
                }
            }
            for(int i=0; i<paths.size(); i++){
                if(i%2==1){
                    if(!paths.get(i).getFromStation().equals(paths.get(i).getToStation())){
                        paths.get(i-1).setError(true);
                    }
                }
            }
            boolean hasError = false;
            Iterator<Path> iterator = paths.iterator();
            while(iterator.hasNext()){
                Path path = iterator.next();
                if(!hasError){
                    hasError = path.isError();
                }
                else if(path.getFromStation().equals(path.getToStation())){
                    iterator.remove();
                }
                else{
                    iterator.remove();
                }
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error reading log files!");
        }
    }
    
    private long getTimeDiff(Date date1, Date date2){
        return date1.getTime()-date2.getTime();
    }
    
}
