package com.simulation.reticlehandlersimulation;

import com.simulation.reticlehandlersimulation.model.FinalDestination;
import com.simulation.reticlehandlersimulation.model.Reticle;
import com.simulation.reticlehandlersimulation.model.ReticlePointer;
import com.simulation.reticlehandlersimulation.model.Station;
import com.simulation.reticlehandlersimulation.service.ReticleHandlerService;
import com.simulation.reticlehandlersimulation.service.ReticlePointerService;
import com.simulation.reticlehandlersimulation.util.StationsInventory;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ReticleSelection extends javax.swing.JFrame {

    private final ReticleHandlerService reticleHandlerService;
    private final StationsInventory stationsInventory;
    private final ReticlePointerService reticlePointerService;
    
    private final List<Reticle> reticles;
    private final List<Station> stations;
    private final List<ReticlePointer> reticlePointers;
    private final List<FinalDestination> finalDestinations;
    
    private final File logFile;
    
    public ReticleSelection(File logFile) {
        this.logFile = logFile;
        stationsInventory = new StationsInventory();
        stations = stationsInventory.getAllStations();
        reticleHandlerService = new ReticleHandlerService(logFile);
        reticles = reticleHandlerService.getReticles();
        finalDestinations = reticleHandlerService.getFinalDestinations();
        reticlePointerService = new ReticlePointerService(reticles, stations);
        reticlePointers = reticlePointerService.getReticlePointers();
        initComponents();
        initializeReticleList();
    }
    
    private void initializeReticleList(){
        DefaultListModel model = new DefaultListModel();
        jList1.setModel(model);
        if(reticlePointers.isEmpty()){
            openLogSelectionFrame();
        }
        else{
            for(ReticlePointer pointers: reticlePointers){
                model.addElement(pointers.getReticleName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("SELECT RETICLE");

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Run Selected Reticle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Run All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jList1.getSelectedIndex()==-1){
            JOptionPane.showMessageDialog(rootPane, "Please select a reticle.");
        }
        else{
            int x = jList1.getSelectedIndex();
            ReticlePointer reticlePointer = reticlePointers.get(x);
            while(reticlePointers.size()>0){
                reticlePointers.remove(0);
            }
            reticlePointers.add(reticlePointer);
            runAnimation();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void runAnimation(){
        AnimationInitializer animationInitializer = new AnimationInitializer(logFile, reticles, stations, reticlePointers, finalDestinations);
        animationInitializer.startAnimation();
        this.dispose();
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        runAnimation();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void openLogSelectionFrame(){
        LogSelectionFrame logSelectionFrame = new LogSelectionFrame();
        logSelectionFrame.setVisible(true);
        this.dispose();
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        openLogSelectionFrame();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
