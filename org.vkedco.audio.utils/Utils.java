package org.vkedco.audio.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vladimir kulyukin
 */
public class Utils {
    
    public static double[] readInPrimDoublesFromLineFile(String inpath) {
        ArrayList<Double> nonPrimDoubles = new ArrayList<>();
        double[] primDoubles = null;
        
        try {
            BufferedReader bufRdr = new BufferedReader(new FileReader(new File(inpath) ) );
            String line = null;
            while ( (line = bufRdr.readLine() ) != null ) {
                nonPrimDoubles.add(Double.valueOf(line));
            }
            
            primDoubles = new double[nonPrimDoubles.size()];
            int i = 0;
            for(Double d: nonPrimDoubles) {
                primDoubles[i++] = d;
            }
            nonPrimDoubles.clear();
            nonPrimDoubles = null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return primDoubles;
    }
    
}
