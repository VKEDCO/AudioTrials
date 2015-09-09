package org.vkedco.calc.utils;

/**
 *
 * @author vladimir kulyukin
 */
public class Ripples_F_p25 extends Function {
    
    public Ripples_F_p25() {}
    @Override
    public double v(double x) { return Math.sin(4*Math.PI*x/512.0); }
   
    
}
