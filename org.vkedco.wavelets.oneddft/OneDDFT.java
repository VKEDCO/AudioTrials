package org.vkedco.wavelets.oneddft;

/**
 *
 * @author Vladimir Kulyukin
 */

import org.jscience.mathematics.number.Complex;

public class OneDDFT {
    
    public static final Complex J = Complex.valueOf(0, 1);
    public static final Complex TwoPIJ = J.times(2*Math.PI);
    
    public static Complex kthComplexSinusoidAt(int k, int n, int N, int sign, double thresh) {
        if ( n < 0 || N <= 0 ) {
            return null;
        }
        else if ( n == 0 ) {
            return Complex.valueOf(1, 0);
        }
        else {
            Complex x = TwoPIJ.times(sign).times(k).times(n).divide(N).exp();
            double xr = x.getReal();
            double xi = x.getImaginary();
            if (Math.abs(xr) <= thresh ) {  xr = 0; }
            if (Math.abs(xi) <= thresh ) {  xi = 0; }
            return Complex.valueOf(xr, xi);
        }
    }
    
    public static Complex[][] computeDFTMat(int N, int sign, double thresh) {
        if ( N <= 0 ) return null;
        Complex [][] dftMat = new Complex[N][N];
        
        for(int k = 0; k < N; k++) {
            for(int n = 0; n < N; n++) {
                dftMat[k][n] = OneDDFT.kthComplexSinusoidAt(k, n, N, sign, thresh);
            }
        }
        return dftMat;
    }
    
     public static Complex[][] computeInverseDFTMat(int N, int sign, double error) {
        if ( N <= 0 ) return null;
        Complex [][] inverseDFTMat = new Complex[N][N];
        double multiplier = 1.0/N;
        
        for(int k = 0; k < N; k++) {
            for(int n = 0; n < N; n++) {
                inverseDFTMat[k][n] = OneDDFT.kthComplexSinusoidAt(k, n, N, sign, error).times(multiplier);
            }
        }
        return inverseDFTMat;
    }
    
    public static void displayDFTMat(Complex[][] mat) {
        int n = mat.length;
        for(int r = 0; r < n; r++) {
            for(int c = 0; c < n; c++) {
                System.out.print(mat[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    public static void displayComplexVector(Complex[] vec) {
        for(int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + " ");
        }
        System.out.println();
    }
    
    public static void displayRealVector(double[] vec) {
        for(int i = 0; i < vec.length; i++) {
            System.out.print(vec[i] + " ");
        }
        System.out.println();
    }
    
    public static Complex[] convertToComplexVector(double[] vec) {
        int N = vec.length;
        Complex[] rslt = new Complex[N];
        for(int i = 0; i < N; i++) {
            rslt[i] = Complex.valueOf(vec[i], 0);
        }
        return rslt;
    }
    
    public static Complex computeSpectrumCell(Complex[][] dftMat, Complex[] signal, int cellNum) {
        if ( dftMat.length != signal.length ) return null;
        if ( cellNum >= dftMat.length ) return null;
        Complex cell = Complex.valueOf(0, 0);
        int N = dftMat.length;
        for(int mat_c = 0; mat_c < N; mat_c++ ) {
            cell = cell.plus(dftMat[cellNum][mat_c].times(signal[mat_c]));
        }
        return cell;
    }
    
    public static Complex[] computeSpectrum(Complex[] signal, int sign, double thresh) {
        if ( signal == null || signal.length == 0 ) return null;
        int N = signal.length;
        Complex[] spectrum = new Complex[N];
        Complex[][] dftMat = OneDDFT.computeDFTMat(N, sign, thresh);
        for(int specCell = 0; specCell < N; specCell++) {
            spectrum[specCell] = OneDDFT.computeSpectrumCell(dftMat, signal, specCell);
        }
        return spectrum;
    }
    
    public static Complex[] computeSpectrum(double[] signal, int sign, double thresh) {
        return OneDDFT.computeSpectrum(OneDDFT.convertToComplexVector(signal), sign, thresh);
    }
    
    public static Complex[] invertSpectrum(Complex[] spectrum, double thresh) {
        if ( spectrum == null || spectrum.length == 0 ) return null;
        int N = spectrum.length;
        Complex[] signal = new Complex[N];
        Complex[][] inverseDFTMat = OneDDFT.computeInverseDFTMat(N, 1, thresh);
        for(int signalCell = 0; signalCell < N; signalCell++) {
            signal[signalCell] = OneDDFT.computeSpectrumCell(inverseDFTMat, spectrum, signalCell);
        }
        return signal;
    }
    
    public static double[] invertSpectrumIntoMagnitudes(Complex[] spectrum, double thresh) {
        if ( spectrum == null || spectrum.length == 0 ) return null;
        int N = spectrum.length;
        double[] signal = new double[N];
        Complex[][] inverseDFTMat = OneDDFT.computeInverseDFTMat(N, 1, thresh);
        for(int signalCell = 0; signalCell < N; signalCell++) {
            signal[signalCell] = OneDDFT.computeSpectrumCell(inverseDFTMat, spectrum, signalCell).magnitude();
        }
        return signal;
    }
    
    public static double[] computeAbsoluteSpectrum(Complex[] signal, int sign, double thresh) {
        if ( signal == null || signal.length == 0 ) return null;
        int N = signal.length;
        double[] absSpectrum = new double[N];
        Complex[][] dftMat = OneDDFT.computeDFTMat(N, sign, thresh);
        for(int specCell = 0; specCell < N; specCell++) {
            absSpectrum[specCell] = OneDDFT.computeSpectrumCell(dftMat, signal, specCell).magnitude();
        }
        return absSpectrum;
    }
     
    public static void dft_real_test(double[] signal) {
        Complex[] csignal = OneDDFT.convertToComplexVector(signal);
        Complex[] spec1 = OneDDFT.computeSpectrum(csignal, -1, 0.001);
        OneDDFT.displayComplexVector(spec1);
    }
    
    public static void inverse_dft_real_test(double[] signal) {
        System.out.print("Original Vector: "); OneDDFT.displayRealVector(signal);
        Complex[] spectrum = OneDDFT.computeSpectrum(OneDDFT.convertToComplexVector(signal), -1, 0.001);
        System.out.print("DFT Spectrum: "); OneDDFT.displayComplexVector(spectrum);
        Complex[] restored_signal = OneDDFT.invertSpectrum(spectrum, 0.001);
        System.out.print("Restored Complex Vector: "); OneDDFT.displayComplexVector(restored_signal);
        double[] restored_magn_signal = OneDDFT.invertSpectrumIntoMagnitudes(spectrum, 0.001);
        System.out.print("Restored Magn Vector: "); OneDDFT.displayRealVector(restored_magn_signal);
    }
    
    public static void inverse_dft_complex_test(Complex[] signal) {
        System.out.print("Original Vector: "); OneDDFT.displayComplexVector(signal);
        Complex[] spectrum = OneDDFT.computeSpectrum(signal, -1, 0.001);
        System.out.print("DFT Spectrum: "); OneDDFT.displayComplexVector(spectrum);
        Complex[] restored_signal = OneDDFT.invertSpectrum(spectrum, 0.001);
        System.out.print("Restored Complex Vector: "); OneDDFT.displayComplexVector(restored_signal);
        double[] restored_magn_signal = OneDDFT.invertSpectrumIntoMagnitudes(spectrum, 0.001);
        System.out.print("Restored Magn Vector: "); OneDDFT.displayRealVector(restored_magn_signal);
    }
    
    public static void dft_complex_test(Complex[] signal) {
        Complex[] spec1 = OneDDFT.computeSpectrum(signal, -1, 0.001);
        OneDDFT.displayComplexVector(spec1);
    }
    
    public static void dft_abs_real_test(double[] signal) {
        Complex[] csignal = OneDDFT.convertToComplexVector(signal);
        double[] absSpec = OneDDFT.computeAbsoluteSpectrum(csignal, -1, 0.001);
        OneDDFT.displayRealVector(absSpec);
    }
    
    public static void dft_abs_complex_test(Complex[] signal) {
        double[] absSpec = OneDDFT.computeAbsoluteSpectrum(signal, -1, 0.001);
        OneDDFT.displayRealVector(absSpec);
    }
    
    public static void test_dft() {
         // real signals
        final double[] signal00 = {5};
        final double[] signal01 = {6, 2};
        final double[] signal02 = {1, 2, 3};
        final double[] signal03 = {2, 3, 5, 7};
        final double[] signal04 = {10, 11, 50, 32, 1};
        final double[] signal05 = {101, 2, 234, 89, 78, 5, 90, 453};
        
        // complex signals
        final Complex[] csignal00 = {Complex.valueOf(5, 3)};
        final Complex[] csignal01 = {Complex.valueOf(4, -1), Complex.valueOf(10, 4)};
        final Complex[] csignal02 = {Complex.valueOf(1, 2), Complex.valueOf(7, -8), Complex.valueOf(19, 21)};
      
        System.out.print("DFT00 = "); dft_real_test(signal00);
        System.out.print("DFT01 = "); dft_real_test(signal01);
        System.out.print("DFT02 = "); dft_real_test(signal02);
        System.out.print("DFT03 = "); dft_real_test(signal03);
        System.out.print("DFT04 = "); dft_real_test(signal04);
        System.out.print("DFT05 = "); dft_real_test(signal05);
        
        System.out.print("|DFT00| = "); dft_abs_real_test(signal00);
        System.out.print("|DFT01| = "); dft_abs_real_test(signal01);
        System.out.print("|DFT02| = "); dft_abs_real_test(signal02);
        System.out.print("|DFT03| = "); dft_abs_real_test(signal03);
        System.out.print("|DFT04| = "); dft_abs_real_test(signal04);
        System.out.print("|DFT05| = "); dft_abs_real_test(signal05);
        
        System.out.print("CDFT00 = "); dft_complex_test(csignal00);
        System.out.print("CDFT01 = "); dft_complex_test(csignal01);
        System.out.print("CDFT02 = "); dft_complex_test(csignal02);
        
        System.out.print("|CDFT00| = "); dft_abs_complex_test(csignal00);
        System.out.print("|CDFT01| = "); dft_abs_complex_test(csignal01);
        System.out.print("|CDFT02| = "); dft_abs_complex_test(csignal02);
    }
    
    public static void test_inverse_dft() {
        final double[] signal00 = {5};
        final double[] signal01 = {6, 2};
        final double[] signal02 = {1, 2, 3};
        final double[] signal03 = {2, 3, 5, 7};
        final double[] signal04 = {10, 11, 50, 32, 1};
        final double[] signal05 = {101, 2, 234, 89, 78, 5, 90, 453};
        
        // complex signals
        final Complex[] csignal00 = {Complex.valueOf(5, 3)};
        final Complex[] csignal01 = {Complex.valueOf(4, -1), Complex.valueOf(10, 4)};
        final Complex[] csignal02 = {Complex.valueOf(1, 2), Complex.valueOf(7, -8), Complex.valueOf(19, 21)};
        
        System.out.print("DFT00 = "); inverse_dft_real_test(signal00);
        System.out.print("DFT01 = "); inverse_dft_real_test(signal01);
        System.out.print("DFT02 = "); inverse_dft_real_test(signal02);
        System.out.print("DFT03 = "); inverse_dft_real_test(signal03);
        System.out.print("DFT04 = "); inverse_dft_real_test(signal04);
        System.out.print("DFT05 = "); inverse_dft_real_test(signal05);
        System.out.print("CDFT00 = "); inverse_dft_complex_test(csignal00);
        System.out.print("CDFT01 = "); inverse_dft_complex_test(csignal01);
        System.out.print("CDFT02 = "); inverse_dft_complex_test(csignal02);
    }
    
    public static void displayKthComplexSinusoid(int k, int N, int sign, double thresh) {
        for(int n = 0; n < N; n++) {
            System.out.print(OneDDFT.kthComplexSinusoidAt(k, n, N, sign, thresh)+" ");
        }
        System.out.println();
    }
    
    public static void displayMaxFreqMagn(Complex[] spectrum) {
        int N = spectrum.length;
        double magn = 0.0;
        int j = 0;
        for(int i = 0; i < N; i++) {
           double temp = spectrum[i].magnitude();
           if ( temp > magn ) {
               j = i;
               magn = temp;
           }
        }
        
        System.out.println(j + " --> " + magn);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        test_inverse_dft();
       
        
    }
}

