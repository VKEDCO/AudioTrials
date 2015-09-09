package org.vkedco.audio.music.piano88A440.octave3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.vkedco.audio.event.detection.AudioEventDetection;
import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;

public class ThirdOctaveFourierDetector {
    
    public static HashMap<Integer, ArrayList<String>> 
            detectFourierKeysByCoeffMagnitude(String file, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, 
            double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        fds.add(new IsA3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsA3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsB3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsC3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsC3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsD3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsD3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsE3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsF3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsF3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsG3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsG3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
         
        // maps frames to all keys present in them
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        map = AudioEventDetection.mapFourierAudioFreqEventsInWavFramesByCoeffMagnitude(file, frame_size, channel_num, 
                scaler, xlower, xupper, fds);
        
        if ( map == null ) return null;
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        
        return map;
        
    }
    
   
    public static HashMap<Integer, ArrayList<String>> 
            detectFourierKeysByHarmonicAmplitude(String file, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, 
            double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        fds.add(new IsA3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsA3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsB3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsC3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsC3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsD3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsD3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsE3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsF3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsF3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsG3Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsG3SharpPresent(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        
        // maps frames to all keys present in them
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        map = AudioEventDetection.mapFourierAudioFreqEventsInWavFramesByHarmonicAmplitude(file, frame_size, channel_num, 
                scaler, xlower, xupper, fds);
        
        if ( map == null ) return null;
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        return map;   
    }
}
