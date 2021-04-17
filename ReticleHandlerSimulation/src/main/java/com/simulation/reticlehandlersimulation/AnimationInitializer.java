package com.simulation.reticlehandlersimulation;

import com.simulation.reticlehandlersimulation.model.FinalDestination;
import com.simulation.reticlehandlersimulation.model.Reticle;
import com.simulation.reticlehandlersimulation.model.ReticlePointer;
import com.simulation.reticlehandlersimulation.model.Station;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AnimationInitializer extends JFrame{

    private final List<Reticle> reticles;
    private final List<Station> stations;
    private final List<ReticlePointer> reticlePointers;
    private final List<FinalDestination> finalDestinations;
    private final File logFile;
    
    public AnimationInitializer(File logFile, List<Reticle> reticles, List<Station> stations, List<ReticlePointer> reticlePointers, List<FinalDestination> finalDestinations) {
        this.reticles = reticles;
        this.stations = stations;
        this.reticlePointers = reticlePointers;
        this.finalDestinations = finalDestinations;
        this.logFile = logFile;
        initUI();
    }
    
    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(new ReticlePointersPanel(reticlePointers));
        mainPanel.add(new AnimationFrame(stations, reticles, reticlePointers, finalDestinations));
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonActionPerformed();
            }
        });
        btnPanel.add(backButton);
        mainPanel.add(btnPanel);
        add(mainPanel);
        setResizable(false);
        pack();
        setTitle("Reticle Handler");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }
    
    public void startAnimation(){
        EventQueue.invokeLater(()->{
            this.setVisible(true);
        });
    }
    
    private void backButtonActionPerformed(){
        ReticleSelection reticleSelection = new ReticleSelection(logFile);
        reticleSelection.setVisible(true);
        this.dispose();
    }
    
}
