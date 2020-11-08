package com.simulation.reticlehandlersimulation;

import com.simulation.reticlehandlersimulation.model.Reticle;
import com.simulation.reticlehandlersimulation.service.ReticleHandlerService;
import com.simulation.reticlehandlersimulation.test.ReticleMotionTest;
import java.io.File;
import java.util.List;

public class MotionFrame extends javax.swing.JFrame {

    private ReticleHandlerService reticleHandlerService;
    private ReticleMotionTest reticleMotionTest;
    
    private List<Reticle> reticles;
    
    public MotionFrame(File logFile) {
        initializeReticles(logFile);
        initComponents();
    }
    
    private void initializeReticles(File logFile){
        reticleHandlerService = new ReticleHandlerService(logFile);
        reticles = reticleHandlerService.getReticles();
        reticleMotionTest = new ReticleMotionTest(reticles);
        reticleMotionTest.testMulti();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
