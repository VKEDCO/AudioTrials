package org.vkedco.audio.music.piano88A440.octave5;

import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;
import org.vkedco.audio.music.piano88A440.Piano88A440KeysAndFreqs;
import org.vkedco.audio.music.piano88A440.Piano88A440NoteFrequencyRanges;

/**
 *
 * @author vladimir kulyukin
 */
public class IsE5Present extends FourierAudioFreqEventDetector {
    
    static final String MESSAGE     = "E5-MI5 Present ";
    static final String DUAL_NAME   = "E5-MI5";
    static final String LETTER_NAME = Piano88A440KeysAndFreqs.E5;
    
    public IsE5Present(double xlower, double xupper, double step, double cthresh, double athresh, double error_margin) {
        super((int)(Piano88A440KeysAndFreqs.E5_FREQ - Piano88A440NoteFrequencyRanges.BAND), 
              (int)(Piano88A440KeysAndFreqs.E5_FREQ + Piano88A440NoteFrequencyRanges.BAND), 
                xlower, xupper, step, cthresh, athresh, error_margin);
    }
    
    @Override
    public String message() { return MESSAGE; }
    
    @Override
    public String getDualName() { return DUAL_NAME; }
    
    @Override
    public String getLetterName() { return LETTER_NAME; }
}
