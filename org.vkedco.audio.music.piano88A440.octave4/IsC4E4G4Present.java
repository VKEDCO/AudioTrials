package org.vkedco.audio.music.piano88A440.octave4;

import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;

/**
 ****************************
 * @author vladimir kulyukin
 ****************************
 */
public class IsC4E4G4Present extends FourierAudioFreqEventDetector {
    
    static final String MESSAGE     = "C Major Present ";
    static final String NOTE_NAME   = "C Major";
    static final String DUAL_NAME   = "DO4-MI4-SOL4/C4-E4-G4";
    static final String LETTER_NAME = "C4-E4-G4";
    
    public IsC4E4G4Present(double xlower, double xupper, double step, double cthresh, double athresh, double error_margin) {
        super(0, 0, xlower, xupper, step, cthresh, athresh, error_margin);
    }
    
    @Override
    public boolean detectByCoeffMagnitude(double[] sample_sequence) {
        IsC4Present do_c4 = new IsC4Present(this.mXLower, this.mXUpper, this.mStep, this.mCoeffThresh, 
                this.mAmpThresh, this.mErrorMargin);
        IsE4Present mi_e4 = new IsE4Present(this.mXLower, this.mXUpper, this.mStep, this.mCoeffThresh, 
                this.mAmpThresh, this.mErrorMargin);
        IsG4Present sol_g4 = new IsG4Present(this.mXLower, this.mXUpper, this.mStep, this.mCoeffThresh, 
                this.mAmpThresh, this.mErrorMargin);
        
        return do_c4.detectByCoeffMagnitude(sample_sequence) && mi_e4.detectByCoeffMagnitude(sample_sequence) && sol_g4.detectByCoeffMagnitude(sample_sequence);
    }
    
    @Override
    public String message() { return MESSAGE; }
    
    @Override
    public String getNoteName() { return NOTE_NAME; }
    
    @Override
    public String getLetterName() { return LETTER_NAME; }
    
}
