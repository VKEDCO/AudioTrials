package org.vkedco.nlp.audiotrials;

// Bugs to vladimir dot kulyukin at gmail dot com.

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class WavAudioDictionary {
    
    // change this directory to where your audio files are stored.
    static final String AUDIO_DIR = 
            "C:/Users/Vladimir/Dropbox/MyShare/TEACHING/CS6890_NLP/support_materials/audio_files/";
    
    static final String AUDIO_DICTIONARY = AUDIO_DIR + "wav_audio_dictionary/";

    static TreeMap<String, ArrayList<Double>> mWords = null;

    static {
        mWords = new TreeMap<String, ArrayList<Double>>();
    }

    static void addAudioAmpList(String word, ArrayList<Double> amp_list) {
        mWords.put(word, amp_list);
    }

    static ArrayList<Double> getAmpList(String word) {
        return mWords.get(word);
    }

    static void buildWavAudioDictionary(String dir_path, int ch_num, int frame_sample_size,
            double zcr_thresh, double amp_thresh) {
        File dir = new File(dir_path);

        if (dir.isDirectory()) {
            for (String f : dir.list(new FilenameFilter() {

                public boolean accept(File directory, String fileName) {
                    return fileName.endsWith(".wav");
                }
            })) {
                System.out.println(f);
                WavAudioDictionary.addAudioAmpList(f.substring(0, f.indexOf(".")),
                        wavFileToNonSilenceAmpArrayList(dir_path + f, ch_num, frame_sample_size, zcr_thresh, amp_thresh));
            }
        }
    }

    static ArrayList<Double> wavFileToNonSilenceAmpArrayList(String wav_in_path,
            int ch_num, int frame_sample_size,
            double zcr_thresh, double amp_thresh) {

        ArrayList<Double> amp_list = new ArrayList<Double>();

        try {
            int frames_read = 0;
            double currZCR = 0;
            double currAmp = 0.0;
            WavFile inWavFile = WavFile.openWavFile(new File(wav_in_path));
            long sample_rate = inWavFile.getSampleRate();
            double normalizer = WavFileManip.convertFrameSampleSizeToSeconds((int) sample_rate, frame_sample_size);

            // Get the number of audio channels in the wav file
            int num_channels = inWavFile.getNumChannels();

            System.out.println("WavFile's number of frames: " + inWavFile.getNumFrames());
            System.out.println("WavFile's sample rate: " + inWavFile.getSampleRate());

            // Create a buffer of frame_sample_size frames
            double[][] buffer = new double[num_channels][frame_sample_size];

            do {
                frames_read = inWavFile.readFrames(buffer, frame_sample_size);
                currZCR = ZeroCrossingRate.computeZCR01(buffer[ch_num], normalizer);
                currAmp = WavFileManip.computeAvrgAbsAmplitude(buffer[ch_num]);

                //if ( framesRead > 0 ) currFrameSampleNum++;

                if (currZCR > zcr_thresh || currAmp > amp_thresh) {
                    //System.out.println("frames_read == " + frames_read);
                    if (frames_read > 0) {
                        for (int i = 0; i < buffer[ch_num].length; i++) {
                            //System.out.println("adding " + buffer[ch_num][i]);
                            amp_list.add(new Double(buffer[ch_num][i]));
                        }
                    }
                }
            } while (frames_read != 0);

            inWavFile.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WavFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return amp_list;
    }
    
    static void calcium_against_calcium_test() {
        ArrayList<Double> amp_list01 =
                WavAudioDictionary
                    .wavFileToNonSilenceAmpArrayList(AUDIO_DIR + "calcium.wav",
                                                    0, 200, 1270.0, 0.005);

        ArrayList<Double> amp_list02 = WavAudioDictionary.getAmpList("calcium");
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("here we go");
        double[][] cost_matrix2 = DTW.dp_dtw_2col_cost_matrix(amp_list01, amp_list02, isim);
        System.out.println("Cost == " + cost_matrix2[amp_list01.size() - 1][1]);
    }

    static void fat_against_fat_test() {
        ArrayList<Double> amp_list01 =
                WavAudioDictionary
                    .wavFileToNonSilenceAmpArrayList(AUDIO_DIR + "fat.wav",
                                                     0, 200, 1270.0, 0.005);

        ArrayList<Double> amp_list02 = WavAudioDictionary.getAmpList("fat");
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("here we go");
        double[][] cost_matrix2 = DTW.dp_dtw_2col_cost_matrix(amp_list01, amp_list02, isim);
        System.out.println("Cost == " + cost_matrix2[amp_list01.size() - 1][1]);
    }

    static void fat_against_fact_test() {
        ArrayList<Double> amp_list01 =
                WavAudioDictionary
                    .wavFileToNonSilenceAmpArrayList(AUDIO_DIR + "fat.wav",
                0, 200, 1270.0, 0.005);

        ArrayList<Double> amp_list02 = WavAudioDictionary.getAmpList("fact");
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("here we go");
        double[][] cost_matrix2 = DTW.dp_dtw_2col_cost_matrix(amp_list01, amp_list02, isim);
        System.out.println("Cost == " + cost_matrix2[amp_list01.size() - 1][1]);
    }

    static void calorie_against_calories_test() {
        ArrayList<Double> amp_list01 =
                WavAudioDictionary
                    .wavFileToNonSilenceAmpArrayList(AUDIO_DIR + "calorie.wav",
                0, 200, 1270.0, 0.005);

        ArrayList<Double> amp_list02 = WavAudioDictionary.getAmpList("calories");
        IDTWSimilarity isim = new AbsModSim();
        System.out.println("here we go");
        double[][] cost_matrix2 = DTW.dp_dtw_2col_cost_matrix(amp_list01, amp_list02, isim);
        System.out.println("Cost == " + cost_matrix2[amp_list01.size() - 1][1]);
    }

    public static void main(String[] args) {
        WavAudioDictionary.buildWavAudioDictionary(AUDIO_DICTIONARY, 0, 200, 1270.0, 0.005);
        System.out.println(WavAudioDictionary.mWords.size());
        WavAudioDictionary.calorie_against_calories_test();
    }
}
