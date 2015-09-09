package org.vkedco.audio.music.piano88A440.octave2;

import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;
import org.vkedco.audio.music.piano88A440.Piano88A440KeysAndFreqs;
import org.vkedco.audio.music.piano88A440.Piano88A440NoteFrequencyRanges;

 /*
 *****************************
 * @author Vladimir Kulyukin
 *****************************
 */

public class IsC2SharpPresent extends FourierAudioFreqEventDetector {
    
    static final String MESSAGE     = "D02#-C2# Present ";
    static final String DUAL_NAME   = "DO2#-C2#";
    static final String LETTER_NAME = Piano88A440KeysAndFreqs.C2_SHARP;
    
    public IsC2SharpPresent(double xlower, double xupper, double step, double cthresh, double athresh, double error_margin) {
        super((int)(Piano88A440KeysAndFreqs.C2_SHARP_FREQ - Piano88A440NoteFrequencyRanges.BAND), 
              (int)(Piano88A440KeysAndFreqs.C2_SHARP_FREQ + Piano88A440NoteFrequencyRanges.BAND), 
                xlower, xupper, step, cthresh, athresh, error_margin);
    }
    
    @Override
    public String message() { return MESSAGE; }
    
    @Override
    public String getDualName() { return DUAL_NAME; }
    
    @Override
    public String getLetterName() { return LETTER_NAME; }
}
