package org.vkedco.audio.event.detection;

import java.io.IOException;
import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.vkedco.audio.wavlib.WavFile;
import org.vkedco.audio.wavlib.WavFileException;
import org.vkedco.tolstov.chapter_01.FourierSeriesExpansion;
import org.vkedco.tolstov.utils.Harmonic;

/**
 ********************************
 * @author Vladimir Kulyukin
 ********************************
 */
public class AudioEventDetection {
    
    // display all harmonics in in_path of a specific frame_sample_size
    // in frequency range [freq_range_start, freq_range_end]
    // xlower, xupper is typically [-Math.PI, Math.PI] by thresholding
    // by amplitude
    public static void displayFourierHarmonicsInWavFramesByHarmonicAmplitude(String in_path, int frame_sample_size, int channelNum, 
            int freq_range_start, int freq_range_end, double xlower, double xupper, double ath) {
        try {

            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            // inWavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = inWavFile.getNumChannels();
            long sampleRate = inWavFile.getSampleRate();

            System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_sample_size frames
            double[][] buffer = new double[numChannels][frame_sample_size];

            int framesRead;
            //int framesWritten;
            //long sample_rate = inWavFile.getSampleRate();
            // normalizer is a number of seconds
            //double normalizer = WavFileManip.convertFrameSampleSizeToSeconds((int) sampleRate, frame_sample_size);
            int currFrameSampleNum = 0;
            double norm_step = (xupper - xlower)/frame_sample_size;

            do {
                framesRead = inWavFile.readFrames(buffer, frame_sample_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                    
                   ArrayList<Harmonic> hlist = FourierSeriesExpansion.expandFromFunctionDataSortedByAmplitude(buffer[channelNum], freq_range_start, freq_range_end, xlower, xupper, norm_step);
                   System.out.print("F" + currFrameSampleNum + ": ");
                    for(Harmonic h: hlist) {
                        if ( h.getA() >= ath )
                             System.out.print(h.toString() + "; ");
                    }
                    System.out.println();
                }

            } while (framesRead != 0);

            inWavFile.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(String in_path, int frame_size, int channelNum, 
            double xlower, double xupper, FourierAudioFreqEventDetector fd) {
        try {

            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            // inWavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = inWavFile.getNumChannels();
            // this is the same as frame_size
            long sampleRate = inWavFile.getSampleRate();

            System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_size frames
            double[][] buffer = new double[numChannels][frame_size];

            int framesRead;
            int currFrameSampleNum = 0;
            double norm_step = (xupper - xlower)/frame_size;

            int count_frames = 0;
            do {
                framesRead = inWavFile.readFrames(buffer, frame_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                   
                    if ( fd.detectByCoeffMagnitude(buffer[channelNum]) ) {
                        System.out.println(fd.message() + currFrameSampleNum);
                    }
                    
                    count_frames++;
                }

            } while (framesRead != 0);

            inWavFile.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public static void displayFourierAudioFreqEventsInWavFramesByAmplitude(String in_path, int frame_size, int channelNum, 
            double xlower, double xupper, FourierAudioFreqEventDetector fd) {
        try {

            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            // inWavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = inWavFile.getNumChannels();
            // this is the same as frame_size
            long sampleRate = inWavFile.getSampleRate();

            System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_size frames
            double[][] buffer = new double[numChannels][frame_size];

            int framesRead;
            int currFrameSampleNum = 0;
            double norm_step = (xupper - xlower)/frame_size;

            int count_frames = 0;
            do {
                framesRead = inWavFile.readFrames(buffer, frame_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                   
                    if ( fd.detectByHarmonicAmplitude(buffer[channelNum]) ) {
                        System.out.println(fd.message() + currFrameSampleNum);
                    }
                    
                    count_frames++;
                }

            } while (framesRead != 0);

            inWavFile.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
     // frame to note name
     public static HashMap<Integer, ArrayList<String>> mapFourierAudioFreqEventsInWavFramesByCoeffMagnitude(String in_path, int frame_size, int channelNum, 
            double scaler, double xlower, double xupper, ArrayList<FourierAudioFreqEventDetector> fds) {
        try {

            HashMap<Integer, ArrayList<String>> map = new HashMap<>();
            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            // inWavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = inWavFile.getNumChannels();
            // this is the same as frame_size
            //long sampleRate = inWavFile.getSampleRate();

            //System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            //System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_size frames
            double[][] buffer = new double[numChannels][frame_size];

            int framesRead;     
            int currFrameSampleNum = -1;
     
            do {
                framesRead = inWavFile.readFrames(buffer, frame_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                    Integer frameNum = new Integer(currFrameSampleNum);
                    ArrayList<String> eventNames = new ArrayList<>();
                    
                    for(int sn = 0; sn < frame_size; sn++) {
                        for(int cn = 0; cn < numChannels; cn++)
                            buffer[cn][sn] += scaler;
                    }
                    
                    for(FourierAudioFreqEventDetector fd: fds) {
                        if ( fd.detectByCoeffMagnitude(buffer[channelNum]) ) {
                            eventNames.add(fd.getLetterName());
                        }
                    }
                    map.put(frameNum, eventNames);
                }

            } while (framesRead != 0);

            inWavFile.close();
            return map;
        } catch (IOException | WavFileException e) {
            System.err.println(e);
            return null;
        }
    }
     
     // frame to note name
     public static HashMap<Integer, ArrayList<String>> 
             mapFourierAudioFreqEventsInWavFramesByHarmonicAmplitude(String in_path, int frame_size, int channelNum, 
                double scaler, double xlower, double xupper, ArrayList<FourierAudioFreqEventDetector> fds) {
        try {

            HashMap<Integer, ArrayList<String>> map = new HashMap<>();
            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            // inWavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = inWavFile.getNumChannels();
            // this is the same as frame_size
            //long sampleRate = inWavFile.getSampleRate();

            //System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            //System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_size frames
            double[][] buffer = new double[numChannels][frame_size];

            int framesRead;
            int currFrameSampleNum = -1;
          
            do {
                framesRead = inWavFile.readFrames(buffer, frame_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                    Integer frameNum = new Integer(currFrameSampleNum);
                    ArrayList<String> eventNames = new ArrayList<>();
                    
                    for(int sn = 0; sn < frame_size; sn++) {
                        for(int cn = 0; cn < numChannels; cn++)
                            buffer[cn][sn] += scaler;
                    }
                    
                    for(FourierAudioFreqEventDetector fd: fds) {
                        if ( fd.detectByHarmonicAmplitude(buffer[channelNum]) ) {
                            eventNames.add(fd.getLetterName());
                        }
                    }
                    map.put(frameNum, eventNames);
                }

            } while (framesRead != 0);

            inWavFile.close();
            return map;
        } catch (IOException | WavFileException e) {
            System.err.println(e);
            return null;
        }
    }
    
    
    
    
    
}
