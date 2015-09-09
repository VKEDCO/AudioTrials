package org.vkedco.audio.event.detection;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.vkedco.audio.wavlib.WavDictionary;
import org.vkedco.audio.wavlib.WavSpeech;
import org.vkedco.cs.dtw.IDTWSimilarity;
import org.vkedco.cs.dtw.DTW;
import org.vkedco.cs.dtw.IWarpingWindow;
import org.vkedco.cs.dtw.AbsModSim;

/**
 *****************************
 * @author Vladimir Kulyukin
 ***************************** 
 */
public class DTWAudioTrials {

    // These must be changed
    static final String SMALL_AUDIO_DIR_LX = "/home/vladimir/Dropbox/MyShare/TEACHING/CS6890_NLP/support_materials/audio_files/small_wav_audio_dictionary/";
    static final String BIG_AUDIO_DIR_LX = "/home/vladimir/Dropbox/MyShare/TEACHING/CS6890_NLP/support_materials/audio_files/wav_audio_dictionary/";
    static final String SMALL_AUDIO_DIR_WIN = "C:\\Users\\Vladimir\\Dropbox\\MyShare\\TEACHING\\CS6890_NLP\\support_materials\\audio_files\\small_wav_audio_dictionary\\";
    static final String BIG_AUDIO_DIR_WIN = "C:\\Users\\Vladimir\\Dropbox\\MyShare\\TEACHING\\CS6890_NLP\\support_materials\\audio_files\\wav_audio_dictionary\\";
    static final int FRAME_SAMPLE_SIZE = 2048;
    private static Object WavSilence;
    
    static void match_audio_file(String dict_dir, String input_word, String dict_word) {
        final double[] input_array = WavSpeech.wavFileToNonSilenceAmpArray(dict_dir + input_word + ".wav",
                                                                0, FRAME_SAMPLE_SIZE, 1270.0, 0.005);
        final double[] dict_array = WavDictionary.getAmpArray(dict_word);
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("Doing DTW...");
        long startTime = System.nanoTime();
        double[][] cost_matrix = DTW.dpDTWTwoColDoubleCostMatrix(input_array, dict_array,
                isim, DTW.MaxWW);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Cost = " + cost_matrix[dict_array.length - 1][1]);
        System.out.println("Duration = " +  TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS));
    }
    
    // 1. Build a segment dictionary
    // 2. Build a segment array from a word
    // 3. Match on the 1st n segments and output the score
    // zcr_thresh = 8000.0; amp_thresh = 0.055
    static void matchAudioFileAgainstSegmentDictionary(String dict_dir, String input_word, int channel_num,
            int frame_sample_size, double zcr_thresh, double amp_thresh, int num_segments, IDTWSimilarity isim,
            IWarpingWindow iww) {
        WavDictionary.buildSegmentDictionary(dict_dir, channel_num, frame_sample_size, zcr_thresh, amp_thresh);
        final ArrayList<double[]> word_segments = WavSpeech.wavFileToListOfNonSilenceSegments(dict_dir + input_word + ".wav",
                                                                0, frame_sample_size, zcr_thresh, amp_thresh);
        double match_score = 0;
        for(String word : WavDictionary.getSegmentKeys()) {
            long startTime = System.nanoTime();
            match_score = 0;
            ArrayList<double[]> dict_word_segments = WavDictionary.getSegmentList(word);
            for(int i = 0; i < num_segments; i++) {
                double sim = DTW.dpDTW2ColDoubleCost(word_segments.get(i), dict_word_segments.get(i), isim, iww);
                match_score += sim;
            }
            long endTime  = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println(input_word + ", " + word + " --> " + match_score);
            System.out.println("Duration = " +  TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS));
        }
    }
    
    static void match_audio_against_array_dictionary(String dict_dir, String input_word) {
        double[] input_ary =
                WavSpeech.wavFileToNonSilenceAmpArray(dict_dir + input_word + ".wav",
                                                 0, FRAME_SAMPLE_SIZE, 1270.0, 0.005);
        double[] dict_ary = null;
        double[][] cost_matrix = null;
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("Doing DTW...");
        for(String curr_word : WavDictionary.getArrayKeys()) {
            System.out.println("Matching against " + curr_word + "...");
            dict_ary = WavDictionary.getAmpArray(curr_word);
            cost_matrix = DTW.dpDTWTwoColDoubleCostMatrix(input_ary, dict_ary, isim, DTW.MaxWW);
            System.out.println("Cost == " + cost_matrix[dict_ary.length - 1][1]);
            dict_ary = null;
            cost_matrix = null;
        }
    }
    
    static void audio_test_01() {
        match_audio_file(BIG_AUDIO_DIR_LX, "calorie", "calorie");
    }
    
    static void audio_test_02() {
        match_audio_file(BIG_AUDIO_DIR_LX, "calorie", "calories");
    }
    
    // detectSilence(String in_path, String out_path, String out_txt_path,
    //        int frame_sample_size, int channel_num, double zcr_thresh, double amp_thresh)
    static void detect_nonsilence_test(String in_path, String out_path, String out_txt_path,
            int frame_sample_size, int channel_num, double zcr_thresh, double amp_thresh) {
        System.out.println(BIG_AUDIO_DIR_LX + in_path + ".wav");
        WavSpeech.detectNonSilence(BIG_AUDIO_DIR_LX + in_path + ".wav", 
                BIG_AUDIO_DIR_LX + out_path + ".wav",
                BIG_AUDIO_DIR_LX + out_txt_path + ".txt", 
                frame_sample_size, channel_num, zcr_thresh, amp_thresh);
    }

    
    public static void main(String[] args) {
        //WavAudioDictionary.buildArrayDictionary(BIG_AUDIO_DIR_LX, 0, FRAME_SAMPLE_SIZE, 1270.0, 0.005);
        //System.out.println(WavDictionary.getArrayDictionarySize());
        //System.out.println("Matching...");
        //DTWAudioTrials.match_audio_against_array_dictionary(BIG_AUDIO_DIR_LX, "fat");
        //DTWAudioTrials.match_audio_file(BIG_AUDIO_DIR_LX, "calorie", "calories");
        // 1270.0 - zcr_thresh; amp_thresh - 0.005
        long startTime = System.nanoTime();
        DTWAudioTrials.matchAudioFileAgainstSegmentDictionary(SMALL_AUDIO_DIR_WIN, "total_carbohydrate", 
                0, 
                FRAME_SAMPLE_SIZE, 
                8000, 
                0.055, 
                3, 
                new AbsModSim(), 
                DTW.OneWW); 
        long endTime  = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Total Duration = " +  TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS));
    }
}
