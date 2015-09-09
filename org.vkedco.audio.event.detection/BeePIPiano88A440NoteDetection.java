package org.vkedco.audio.event.detection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.vkedco.audio.event.detection.fourier.Piano88A440OctaveFourierDetector;

/**
 *
 * @author Vladimir Kulyukin
 */
public class BeePIPiano88A440NoteDetection {
    
    public static final int FREQ_44100 = 44100;
    public static final int FREQ_22050 = 22050;
    public static final int FREQ_11025 = 11025;
    public static final int FREQ_5512  = 5512;
    public static final int FREQ_2756  = 2756;
    
    // July 04, 2015
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_19_21_04jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/19_21_04jul/"; // 19-21
    
    // July 05, 2015
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_22_00_04jul_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/22_00_04jul_05jul/"; // 22-12am
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/01_03_05jul/"; // 1-3am
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/04_06_05jul/"; // 4-6am
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/07_09_05jul/"; // 7-9am
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/10_12_05jul/"; // 10am-12pm
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_13_15_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/13_15_05jul/"; // 1pm-3pm
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_16_18_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/16_18_05jul/"; // 4-6pm
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_19_21_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/19_21_05jul/"; // 6-8pm
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_22_00_05jul_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/22_00_05jul_06jul/"; // 22-12am
    
    // July 06, 2015
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_01_03_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/01_03_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_04_06_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/04_06_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_07_09_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/07_09_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_10_12_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/10_12_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_13_15_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/13_15_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_16_18_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/16_18_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_19_21_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/19_21_06jul/"; // 6-8pm
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_17_21_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/17_21_06jul/";
    public static final String BUZZ_DIR_F02_USOF_25Jul2015_22_00_06jul_07jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/22_00_06jul_07jul/";
    
    
    public static void detectAllPiano88A440Octaves(String file_path, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {
        HashMap<Integer, ArrayList<String>> octave0_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 0, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave1_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 1, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave2_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 2, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave3_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 3, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave4_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 4, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave5_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 5, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave6_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 6, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave7_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 7, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        HashMap<Integer, ArrayList<String>> octave8_map = 
                Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, 8, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
        
        
        Piano88A440OctaveFourierDetector.displayAllHarmonicsPerFrame(octave0_map, 
                octave1_map, octave2_map, octave3_map, octave4_map, octave5_map, 
                octave6_map, octave7_map, octave8_map, 2000);
    }
    
    public static ArrayList<HashMap<Integer, ArrayList<String>>> mapAllPiano88A440Octaves(String file_path, 
            int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {
        
        ArrayList<HashMap<Integer, ArrayList<String>>> octaveMaps = new ArrayList<>();
        HashMap<Integer, ArrayList<String>> octave = null;
        for(int octaveNum = 0; octaveNum <= 8; octaveNum++) {
            octave = computePiano88A440NthOctave(file_path, octaveNum, channel_num, frame_size,
                                                    scaler, xlower, xupper, cth, ath, error_margin);
            if ( octave != null )
                octaveMaps.add(octave);
        }
       
        return octaveMaps;   
    }
    
    static HashMap<Integer, ArrayList<String>> computePiano88A440NthOctave(String file_path, int octave_num, int channel_num, int frame_size, 
                double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {
        return  Piano88A440OctaveFourierDetector
                    .detectFourierKeysByCoeffMagnitude(file_path, octave_num, channel_num, frame_size,
                                                       scaler, xlower, xupper, cth, ath, error_margin);
    }
    
    static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } 
            else {
                System.out.println(fileEntry.getName());
            }
        }
    }
    
    // freq = 44100, 22050, 11025, 5512, 2756
    static void detectAllPiano8844OctavesAtFreq(String dir, int freq) {   
        File folder = new File(dir);
        String freqStr = Integer.toString(freq);
        for(final File fileEntry: folder.listFiles()) {
            if ( !fileEntry.isDirectory() ) {
                System.out.println(fileEntry.toString() + " at " + freqStr);
                detectAllPiano88A440Octaves(fileEntry.toString(), 
                            0, 
                            freq,
                            0,
                            -Math.PI, 
                            Math.PI, 
                            0.001, 
                            0.001, 
                            0.5);
                System.out.println("=============");
            }
        }
    }
    
    // TODO: Emulate this function to translate the convert all files at a specific
    // directory to logo command files
    static void processDirectory(String dir) {
        detectAllPiano8844OctavesAtFreq(dir, BeePIPiano88A440NoteDetection.FREQ_44100);
        System.out.println("************");
        detectAllPiano8844OctavesAtFreq(dir, BeePIPiano88A440NoteDetection.FREQ_22050);
        System.out.println("************");
        detectAllPiano8844OctavesAtFreq(dir, BeePIPiano88A440NoteDetection.FREQ_11025);
        System.out.println("************");
        detectAllPiano8844OctavesAtFreq(dir, BeePIPiano88A440NoteDetection.FREQ_5512);
        System.out.println("************");
        detectAllPiano8844OctavesAtFreq(dir, BeePIPiano88A440NoteDetection.FREQ_2756);
        System.out.println("************"); 
    }
    
    static void do_13_15_05jul() {
        processDirectory(BUZZ_DIR_F02_USOF_25Jul2015_13_15_05jul);
    }
    
    static void do_16_18_05jul() {
        processDirectory(BUZZ_DIR_F02_USOF_25Jul2015_16_18_05jul);
    }
    
    static void do_19_21_05jul() {
        processDirectory(BUZZ_DIR_F02_USOF_25Jul2015_19_21_05jul);
    }
    
    static void do_22_00_05jul_06jul() {
        processDirectory(BUZZ_DIR_F02_USOF_25Jul2015_22_00_05jul_06jul);
    }
    
    public static void main(String args[]) {
        do_22_00_05jul_06jul();
    }
    
}
