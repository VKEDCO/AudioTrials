package org.vkedco.audio.event.detection.fourier;

/**
 ******************************
 * @author Vladimir Kulyukin
 ****************************** 
 */

public class IsFreqRangePresent extends FourierAudioFreqEventDetector {
    
    static final String MESSAGE     = "";
    static final String DUAL_NAME   = "";
    static final String LETTER_NAME = "";
    
    public IsFreqRangePresent(int freq_range_start, int freq_range_end, double xlower, double xupper, double step, double cthresh, 
            double athresh, double error_margin) {
        super(freq_range_start, freq_range_end, xlower, xupper, step, cthresh, athresh, error_margin);
    }
    
    @Override
    public String message() {
        return MESSAGE;
    }
    
    @Override
    public String getDualName() {
        return DUAL_NAME;
    }
    
    @Override
    public String getLetterName() {
        return LETTER_NAME;
    }
    
}
