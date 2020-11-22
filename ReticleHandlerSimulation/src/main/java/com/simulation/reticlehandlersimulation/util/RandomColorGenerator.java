package com.simulation.reticlehandlersimulation.util;

import java.awt.Color;
import java.util.Random;

public class RandomColorGenerator {

    private Random random;

    public RandomColorGenerator() {
        random = new Random();
    }
    
    public Color getRandomColor(){
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color color = new Color(r, g, b);
        return color.brighter();
    }
        
    
}
