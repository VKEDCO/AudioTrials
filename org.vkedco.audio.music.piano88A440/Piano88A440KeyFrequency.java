package org.vkedco.audio.music.piano88A440;

/*
 ***************************************************************************
 * @author vladimir kulyukin
 * Mappings from keys to frequencies and back on an 88 key A440 piano board.
 ****************************************************************************
 */

public class Piano88A440KeyFrequency {
    
    public static double keyToFrequency(int k) {
        return Math.pow(2.0, (k - 49)/12.0)*440.0;
    }
    
    public static int frequencyToKey(double f) {
        return (int)(12*Math.log(f/440.0)/Math.log(2.0) + 49);
    }
    
    public static void test_0th_octave() {
        int a0key = 1;
        int asharpkey = 2;
        int b0key = 3;
        
        double a0f = Piano88A440KeyFrequency.keyToFrequency(a0key);
        double asharpf = Piano88A440KeyFrequency.keyToFrequency(asharpkey);
        double b0f = Piano88A440KeyFrequency.keyToFrequency(b0key);
        
        System.out.println("A0's f = " + a0f);
        System.out.println("A#'s f = " + asharpf);
        System.out.println("B0's f = " + b0f);
        
        System.out.println("A0's   real   key = " + a0key);
        System.out.println("A0's computed key = " + Piano88A440KeyFrequency.frequencyToKey(a0f));
        System.out.println("A#'s   real   key = " + asharpkey);
        System.out.println("A#'s computed key = " + Piano88A440KeyFrequency.frequencyToKey(asharpf));
        System.out.println("B0's   real   key = " + b0key);
        System.out.println("B0's computed key = " + Piano88A440KeyFrequency.frequencyToKey(b0f));
    }
    
    public static void rule_7_11(int key) {
        
    }
    
    // test mappings of the 4th octave
    public static void test_4th_octave() {
        int c4k = 40;
        int d4k = 42;
        int e4k = 44;
        int f4k = 45;
        int g4k = 47;
        int a4k = 49;
        int b4k = 51;
        
        double c4f = Piano88A440KeyFrequency.keyToFrequency(c4k);
        double d4f = Piano88A440KeyFrequency.keyToFrequency(d4k);
        double e4f = Piano88A440KeyFrequency.keyToFrequency(e4k);
        double f4f = Piano88A440KeyFrequency.keyToFrequency(f4k);
        double g4f = Piano88A440KeyFrequency.keyToFrequency(g4k);
        double a4f = Piano88A440KeyFrequency.keyToFrequency(a4k);
        double b4f = Piano88A440KeyFrequency.keyToFrequency(b4k);
    
        System.out.println("C4's f = " + c4f);
        System.out.println("D4's f = " + d4f);
        System.out.println("E4's f = " + e4f);
        System.out.println("F4's f = " + f4f);
        System.out.println("G4's f = " + g4f);
        System.out.println("A4's f = " + a4f);
        System.out.println("B4's f = " + b4f);
        
        System.out.println("C4's   real   key = " + c4k);
        System.out.println("C4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(c4f));
        System.out.println("D4's   real   key = " + d4k);
        System.out.println("D4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(d4f));
        System.out.println("E4's   real   key = " + e4k);
        System.out.println("E4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(e4f));
        System.out.println("F4's   real   key = " + f4k);
        System.out.println("F4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(f4f));
        System.out.println("G4's   real   key = " + g4k);
        System.out.println("G4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(g4f));
        System.out.println("A4's   real   key = " + a4k);
        System.out.println("A4's computed key = " + Piano88A440KeyFrequency.frequencyToKey(a4f));
    }
    
    public static void main(String[] args) {
        test_0th_octave();
      
    }
}
