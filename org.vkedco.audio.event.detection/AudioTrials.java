package org.vkedco.audio.event.detection;

/**
 ****************************
 * @author Vladimir Kulyukin
 ****************************
 */

import org.vkedco.audio.event.detection.fourier.IsFreqRangePresent;
import org.vkedco.audio.event.detection.fourier.FourierAudioFreqEventDetector;
import org.vkedco.audio.music.piano88A440.octave5.IsG5Present;
import org.vkedco.audio.music.piano88A440.octave5.IsC5Present;
import org.vkedco.audio.music.piano88A440.octave5.IsE5Present;
import org.vkedco.audio.music.piano88A440.octave6.IsD6Present;
import org.vkedco.audio.music.piano88A440.octave5.IsB5Present;
import org.vkedco.audio.music.piano88A440.octave4.IsG4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsF4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsE4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsD4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsC4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsC4E4G4Present;
import org.vkedco.audio.music.piano88A440.octave4.IsA4Present;
import org.vkedco.audio.music.piano88A440.octave0.IsB0Present;
import org.vkedco.audio.music.piano88A440.octave0.IsA0Present;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.sound.sampled.*;
import org.vkedco.audio.wavlib.*;
import org.vkedco.wavelets.oneddft.OneDDFT;
import org.jscience.mathematics.number.Complex;
import org.vkedco.audio.event.detection.fourier.Piano88A440OctaveFourierDetector;
import org.vkedco.tolstov.chapter_01.FourierSeriesExpansion;
import org.vkedco.tolstov.utils.Harmonic;

public class AudioTrials {
    
    // change this to the directory where you place the audio files
    static final String AUDIO_DIR = 
            "C:/Users/Vladimir/Dropbox/MyShare/TEACHING/CS6890_NLP/support_materials/audio_files/";
    
    static final String AUDIO_DIR_02 = 
            "C:/Users/Vladimir/research/audio_files/wav/";
    
    static final String AUDIO_DIR_03 =
            "C:/Users/Vladimir/Android/Desktop/PianoKeyFrequencies/hattes_piano_keys/wav/";
    
    static final String BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/01_03_05jul/"; // 1-3am
    static final String BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/04_06_05jul/"; // 4-6am
    static final String BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/07_09_05jul/"; // 7-9am
    static final String BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/10_12_05jul/"; // 10am-12pm
    static final String BUZZ_DIR_F02_USOF_25Jul2015_13_15_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/13_15_05jul/"; // 1pm-3pm
    static final String BUZZ_DIR_F02_USOF_25Jul2015_16_18_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/16_18_05jul/"; // 4-6pm
    static final String BUZZ_DIR_F02_USOF_25Jul2015_19_21_05jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/19_21_05jul/"; // 6-8pm
    static final String BUZZ_DIR_F02_USOF_25Jul2015_22_00_05jul_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/22_00_05jul_06jul/";
    
    static final String BUZZ_DIR_F02_USOF_25Jul2015_01_03_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/01_03_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_04_06_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/04_06_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_07_09_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/07_09_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_10_12_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/10_12_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_13_15_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/13_15_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_16_18_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/16_18_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_17_21_06jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/17_21_06jul/";
    static final String BUZZ_DIR_F02_USOF_25Jul2015_22_00_06jul_07jul =
            "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/22_00_06jul_07jul/";
    
     static final String[] files_01_03_05jul = {
        "2015-07-05_01-03-46.wav",
        "2015-07-05_01-23-46.wav",
        "2015-07-05_01-43-46.wav",
        "2015-07-05_02-03-46.wav",
        "2015-07-05_02-23-46.wav",
        "2015-07-05_02-43-46.wav",
        "2015-07-05_03-03-46.wav",
        "2015-07-05_03-23-46.wav",
        "2015-07-05_03-43-46.wav"
    };
    
    static final String AUDIO_DICTIONARY = AUDIO_DIR + "small_wav_audio_dictionary/";
    // take a path to an audio file and print various statistics of
    // that file such as total number of frames read, format encoding,
    // sample size, number of channels, etc.
    static void readSoundBytes(String audioPath, String format) {
        int totalFramesRead = 0;
        File fileIn = new File(audioPath);
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(fileIn);

            AudioFormat aufrmt = audioInputStream.getFormat();

            System.out.println("Audio Format: " + audioInputStream.getFormat());
            System.out.println("Audio Format Encoding: " + aufrmt.getEncoding());
            System.out.println("Audio Format Sample Size Bits: "
                    + aufrmt.getSampleSizeInBits());
            System.out.println("Audio Format Num Channels: " + aufrmt.getChannels());
            System.out.println("Audio Format Frame Size in Bytes: " + aufrmt.getFrameSize());

            int bytesPerFrame =
                    audioInputStream.getFormat().getFrameSize();

            if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                bytesPerFrame = 1;
            }

            System.out.println("bytesPerFrame == " + bytesPerFrame);

            int numBytes = 1024 * bytesPerFrame;

            byte[] audioBytes = new byte[numBytes];

            //displayBytes(audioInputStream);

            try {
                int numBytesRead = 0;
                int numFramesRead = 0;
                while ((numBytesRead = audioInputStream.read(audioBytes))
                        != -1) {
                    numFramesRead = numBytesRead / bytesPerFrame;
                    totalFramesRead += numFramesRead;
                    //System.out.println("numFramesRead == " + numFramesRead);
                }
            } catch (Exception ex) {
                System.err.println(ex.toString());
            }

            System.out.print(format + ": ");
            int b;
            for (int i = 0; i < audioBytes.length; i++) {
                b = audioBytes[i];
                System.out.print(b + " ");
            }

        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }
    
    static void audioTrial_01() {
        System.out.println("MS  = " + WavFileManip.convertFrameSampleSizeToMilliSeconds(44100, 200));
        System.out.println("SEC = " + WavFileManip.convertFrameSampleSizeToSeconds(44100, 200));
        System.out.println("Frames = " + WavFileManip.convertMilliSecondsToFrameSampleSize(44100, 4));
        System.out.println("Frames = " + WavFileManip.convertSecondsToFrameSampleSize(44100, 0.25));
    }
    
    static void audioTrial_02() {
        WavFileManip.writeFrameSampleRangeToFile(AUDIO_DIR + "calcium.wav",
                                         AUDIO_DIR + "calcium_ca.wav",
                                         AUDIO_DIR + "calcium_ca.txt",
                                         200, 0, 290, 312);
    }
    
    static void audioTrial_03() {
        WavFileManip.writeFrameSampleRangeToFile(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_a.wav",
                AUDIO_DIR + "calcium_a.txt",
                200, 0, 313, 338);
    }
    
    static void audioTrial_03a() {
        WavFileManip.writeFrameSampleRangeToFile(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_si.wav",
                AUDIO_DIR + "calcium_si.txt",
                200, 0, 338, 400);
    }
    
    static void audioTrial_03b() {
        WavFileManip.writeFrameSampleRangeToFile(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_um.wav",
                AUDIO_DIR + "calcium_um.txt",
                200, 0, 395, 420);
    }
    
    static void audioTrial_03c() {
        WavFileManip.writeFrameSampleRangeToFile(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_all.wav",
                AUDIO_DIR + "calcium_all.txt",
                200, 0, 0, 1400);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file calcium.wav
    static void audioTrial_04() {
        // CALCIUM
        WavFileManip.detectSilence(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_silence.wav",
                AUDIO_DIR + "calcium_silence2.txt",
                200, 0, 1270.0, 0.005);
        
        WavFileManip.detectNonSilence(AUDIO_DIR + "calcium.wav",
                AUDIO_DIR + "calcium_non_silence.wav",
                AUDIO_DIR + "calcium_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
     // detectByCoeffMagnitude silence and non-silence in file calorie.wav
    static void audioTrial_05() {
       // CALORIE
        WavFileManip.detectSilence(AUDIO_DIR + "calorie.wav",
                AUDIO_DIR + "calorie_silence.wav",
                AUDIO_DIR + "calorie_silence2.txt",
                200, 0, 1270.0, 0.005);
        
        WavFileManip.detectNonSilence(AUDIO_DIR + "calorie.wav",
                AUDIO_DIR + "calorie_non_silence.wav",
                AUDIO_DIR + "calorie_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file calories.wav
    static void audioTrial_06() {
        // CALORIES
        WavFileManip.detectSilence(AUDIO_DIR + "calories.wav",
                AUDIO_DIR + "calories_silence.wav",
                AUDIO_DIR + "calories_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "calories.wav",
                AUDIO_DIR + "calories_non_silence.wav",
                AUDIO_DIR + "calories_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file carbohydrates.wav
    static void audioTrial_07() {
        // CARBOHYDRATES
        WavFileManip.detectSilence(AUDIO_DIR + "carbohydrates.wav",
                AUDIO_DIR + "carbohydrates_silence.wav",
                AUDIO_DIR + "carbohydrates_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "carbohydrates.wav",
                AUDIO_DIR + "carbohydrates_non_silence.wav",
                AUDIO_DIR + "carbohydrates_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file cholesterol.wav
    static void audioTrial_08() {
        // CHOLESTEROL
        WavFileManip.detectSilence(AUDIO_DIR + "cholesterol.wav",
                AUDIO_DIR + "cholesterol_silence.wav",
                AUDIO_DIR + "cholesterol_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "cholesterol.wav",
                AUDIO_DIR + "cholesterol_non_silence.wav",
                AUDIO_DIR + "cholesterol_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file dietary_fiber.wav
    static void audioTrial_09() {
         // DIETARY FIBER
        WavFileManip.detectSilence(AUDIO_DIR + "dietary_fiber.wav",
                AUDIO_DIR + "dietary_fiber_silence.wav",
                AUDIO_DIR + "dietary_fiber_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "dietary_fiber.wav",
                AUDIO_DIR + "dietary_fiber_non_silence.wav",
                AUDIO_DIR + "dietary_fiber_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file fact.wav
    static void audioTrial_10() {
        // FACT
        WavFileManip.detectSilence(AUDIO_DIR + "fact.wav",
                AUDIO_DIR + "fact_silence.wav",
                AUDIO_DIR + "fact_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "fact.wav",
                AUDIO_DIR + "fact_non_silence.wav",
                AUDIO_DIR + "fact_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file facts.wav
    static void audioTrial_11() {
        // FACTS
        WavFileManip.detectSilence(AUDIO_DIR + "facts.wav",
                AUDIO_DIR + "facts_silence.wav",
                AUDIO_DIR + "facts_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "facts.wav",
                AUDIO_DIR + "facts_non_silence.wav",
                AUDIO_DIR + "facts_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file ingredient.wav
    static void audioTrial_12() {
        // INGREDIENT
        WavFileManip.detectSilence(AUDIO_DIR + "ingredient.wav",
                AUDIO_DIR + "ingredient_silence.wav",
                AUDIO_DIR + "ingredient_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "ingredient.wav",
                AUDIO_DIR + "ingredient_non_silence.wav",
                AUDIO_DIR + "ingredient_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file ingredients.wav
    static void audioTrial_13() {
        // INGREDIENTS
        WavFileManip.detectSilence(AUDIO_DIR + "ingredients.wav",
                AUDIO_DIR + "ingredients_silence.wav",
                AUDIO_DIR + "ingredients_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "ingredients.wav",
                AUDIO_DIR + "ingredients_non_silence.wav",
                AUDIO_DIR + "ingredients_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file nutrition.wav
    static void audioTrial_14() {
        // NUTRITION
        WavFileManip.detectSilence(AUDIO_DIR + "nutrition.wav",
                AUDIO_DIR + "nutrition_silence.wav",
                AUDIO_DIR + "nutrition_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "nutrition.wav",
                AUDIO_DIR + "nutrition_non_silence.wav",
                AUDIO_DIR + "nutrition_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file nutrition_facts.wav
    static void audioTrial_15() {
        // NUTRITION FACTS
        WavFileManip.detectSilence(AUDIO_DIR + "nutrition_facts.wav",
                AUDIO_DIR + "nutrition_facts_silence.wav",
                AUDIO_DIR + "nutrition_facts_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "nutrition_facts.wav",
                AUDIO_DIR + "nutrition_facts_non_silence.wav",
                AUDIO_DIR + "nutrition_facts_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file saturated_fat.wav
    static void audioTrial_16() {
       // SATURATED FAT
        WavFileManip.detectSilence(AUDIO_DIR + "saturated_fat.wav",
                AUDIO_DIR + "saturated_fat_silence.wav",
                AUDIO_DIR + "saturated_fat_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "saturated_fat.wav",
                AUDIO_DIR + "saturated_fat_non_silence.wav",
                AUDIO_DIR + "saturated_fat_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in file serving_size.wav
    static void audioTrial_17() {
       // SERVING SIZE
        WavFileManip.detectSilence(AUDIO_DIR + "serving_size.wav",
                AUDIO_DIR + "serving_size_silence.wav",
                AUDIO_DIR + "serving_size_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "serving_size.wav",
                AUDIO_DIR + "serving_size_non_silence.wav",
                AUDIO_DIR + "serving_size_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in total_carbohydrate.wav
    static void audioTrial_18() {
        // TOTAL CARBOHYDRATE
        WavFileManip.detectSilence(AUDIO_DIR + "total_carbohydrate.wav",
                AUDIO_DIR + "total_carbohydrate_silence.wav",
                AUDIO_DIR + "total_carbohydrate_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "total_carbohydrate.wav",
                AUDIO_DIR + "total_carbohydrate_non_silence.wav",
                AUDIO_DIR + "total_carbohydrate_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in total_fat.wav
    static void audioTrial_19() {
        // TOTAL FAT
        WavFileManip.detectSilence(AUDIO_DIR + "total_fat.wav",
                AUDIO_DIR + "total_fat_silence.wav",
                AUDIO_DIR + "total_fat_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "total_fat.wav",
                AUDIO_DIR + "total_fat_non_silence.wav",
                AUDIO_DIR + "total_fat_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in trans_fat.wav
    static void audioTrial_20() {
        // TOTAL FAT
        WavFileManip.detectSilence(AUDIO_DIR + "trans_fat.wav",
                AUDIO_DIR + "trans_fat_silence.wav",
                AUDIO_DIR + "trans_fat_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "trans_fat.wav",
                AUDIO_DIR + "trans_fat_non_silence.wav",
                AUDIO_DIR + "trans_fat_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    // detectByCoeffMagnitude silence and non-silence in protein.wav
    static void audioTrial_21() {
         // PROTEIN
        WavFileManip.detectSilence(AUDIO_DIR + "protein.wav",
                AUDIO_DIR + "protein_silence.wav",
                AUDIO_DIR + "protein_silence2.txt",
                200, 0, 1270.0, 0.005);

        WavFileManip.detectNonSilence(AUDIO_DIR + "protein.wav",
                AUDIO_DIR + "protein_non_silence.wav",
                AUDIO_DIR + "protein_non_silence2.txt",
                200, 0, 1270.0, 0.005);
    }
    
    //WavAudioDictionary.buildWavAudioDictionary(AUDIO_DICTIONARY, 0, 200, 1270.0, 0.005);
    //    System.out.println(WavDictionary.mWords.size());
    //    WavDictionary.calorie_against_calories_test();
    static void audioTrial22() {
         WavDictionary.buildSegmentDictionary(AUDIO_DICTIONARY, 0, 2024, 1270.0, 0.005);
         WavDictionary.displayAudioArraySegments();
    }
    
    static void audioTrial24(String file_name, int frame_size, int freq_range_start, int freq_range_end, 
            double ath) {
        AudioEventDetection.displayFourierHarmonicsInWavFramesByHarmonicAmplitude(file_name, frame_size, 
                0, freq_range_start, freq_range_end, -Math.PI, Math.PI, ath);
    }
    
    // detectByCoeffMagnitude DO4-C4
    static void audioTrial25(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting DO4-C4");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        
        IsC4Present detector = new IsC4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude DO5-C5
    static void audioTrial26(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting DO5-C5");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;

        IsC5Present detector = new IsC5Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude SOL5-G5
    static void audioTrial27(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting SOL5-G5");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsG5Present detector = new IsG5Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude C Major
    static void audioTrial28(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsC4E4G4Present detector = new IsC4E4G4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude LA4-A4
    static void audioTrial29(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting LA4-A4");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsA4Present detector = new IsA4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude RE6-D6
    static void audioTrial30(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting RE6-D6");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsD6Present detector = new IsD6Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(AUDIO_DIR_02 + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude FA4-F4
    static void audioTrial31(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting FA4-F4");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsF4Present detector = new IsF4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude LA0-A0
    static void audioTrial32(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting LA0-A0");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsA0Present detector = new IsA0Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    // detectByCoeffMagnitude SI0-B0
    static void audioTrial33(String dir, String file_name, int frame_size, double cth, double ath, double error_margin) {
        System.out.println("Detecting SI0-B0");
        int channel_num = 0;
        double xlower = -Math.PI;
        double xupper = Math.PI;
        IsB0Present detector = new IsB0Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
        AudioEventDetection.displayFourierAudioFreqEventsInWavFramesByAmplitude(dir + file_name, frame_size, channel_num, xlower, xupper, detector);
    }
    
    
    static void detect_DO4_C4(double cth, double ath, double error_margin) {
        int frame_size = 44100; int frame_size2 = 22050;
        audioTrial25(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial25(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    static void detect_DO5_C5(double cth, double ath, double error_margin) {
         int frame_size = 44100; int frame_size2 = 22050;
        audioTrial26(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial26(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    // G5 should be detected only in the files with C4 because
    // G5 is a component harmonic of C4.
    static void detect_SOL5_G5(double cth, double ath, double error_margin) {
         int frame_size = 44100; int frame_size2 = 22050;
        audioTrial27(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial27(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    static void detect_LA4_A4(double cth, double ath, double error_margin) {
        int frame_size = 44100; int frame_size2 = 22050;
        audioTrial29(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial29(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    // D6 should be detected in all audio files where G4 is present
    // because D6 is a component of G4.
    static void detect_RE6_D6(double cth, double ath, double error_margin) {
        int frame_size = 44100; int frame_size2 = 22050;
        audioTrial30(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial30(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    // D6 should be detected in all audio files where G4 is present
    // because D6 is a component of G4.
    static void detect_FA4_F4(double cth, double ath, double error_margin) {
        int frame_size = 44100; int frame_size2 = 22050;
        audioTrial31(AUDIO_DIR_02, "DO4_C4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "LA4_A4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "MI4_E4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "FA4_F4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "DO4_MI4_SOL4_C4_E4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "SOL4_G4.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", frame_size, cth, ath, error_margin);
        System.out.println();
        audioTrial31(AUDIO_DIR_02, "tuning_fork_A4.wav", frame_size2, cth, ath, error_margin);
        System.out.println();
    }
    
    static void detectAllNotesByHarmonicCoeffMagnitude(String dir, String file, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        final double STEP = (xupper-xlower)/frame_size;
        
        fds.add(new IsA0Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsB0Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsC4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsE4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsF4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsG4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsA4Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsC5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsE5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsG5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsB5Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsD6Present(xlower, xupper, STEP, cth, ath, error_margin));
       
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        map = AudioEventDetection.mapFourierAudioFreqEventsInWavFramesByCoeffMagnitude(dir + file, frame_size, 
                channel_num, scaler, xlower, xupper, fds);
        
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        
    }
    
    static void detectAllNotesByHarmonicAmplitude(String dir, String file, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        
        final double STEP = (xupper-xlower)/frame_size;
        fds.add(new IsA0Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsB0Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsC4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsE4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsF4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsG4Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsA4Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsC5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsE5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsG5Present(xlower, xupper, STEP, cth, ath, error_margin));
        fds.add(new IsB5Present(xlower, xupper, STEP, cth, ath, error_margin));
        
        fds.add(new IsD6Present(xlower, xupper, STEP, cth, ath, error_margin));
       
        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        
        map = AudioEventDetection.mapFourierAudioFreqEventsInWavFramesByHarmonicAmplitude(dir + file, frame_size, channel_num, scaler, xlower, xupper, fds);
        
        if ( map == null ) return;
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        
    }
    
    static void detectFrequencyRangeByHarmonicAmplitude(String dir, String file, int channel_num, int frame_size, 
            int freq_range_start, int freq_range_end, double scaler, double xlower, double xupper, double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        
        final double STEP = (xupper-xlower)/frame_size;
        fds.add(new IsFreqRangePresent(freq_range_start, freq_range_end, xlower, xupper, STEP, cth, ath, error_margin));
       
       
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        map = AudioEventDetection
                .mapFourierAudioFreqEventsInWavFramesByHarmonicAmplitude(dir + file, 
                frame_size, channel_num, scaler, xlower, xupper, fds);
        
        if ( map == null ) return;
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        
    }
   
    static void displayBytes(AudioInputStream ais) {
        int b;
        System.out.println("displayBytes");
        try {
            while ((b = ais.read()) != -1) {
                System.out.println("amplitude == " + b);
            }
            System.out.println("done");
        } catch (IOException ex) {
        }
        System.out.println("done");
    }
    
    static void detect4thOctaveNotes(String file, int channel_num, int frame_size, 
            double scaler, double xlower, double xupper, 
            double cth, double ath, double error_margin) {

        ArrayList<FourierAudioFreqEventDetector> fds = new ArrayList<>();
        fds.add(new IsC4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsD4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsE4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsF4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsG4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        fds.add(new IsA4Present(xlower, xupper, (xupper-xlower)/frame_size, cth, ath, error_margin));
        
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        
        map = AudioEventDetection
                .mapFourierAudioFreqEventsInWavFramesByCoeffMagnitude(AUDIO_DIR_02 + file, frame_size, channel_num, 
                scaler, xlower, xupper, fds);
        
        if ( map == null ) return;
        for(Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if ( e.getValue().size() > 0 ) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for(String s : e.getValue() ) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
        
    }
    
    static ArrayList<Integer> audioFileToArrayList(String inputPath) {
        int b;
        ArrayList<Integer> vector = new ArrayList<>();
        try {
            File inFile;
            try {
                inFile = new File(inputPath);
            } catch (NullPointerException ex) {
                System.out.println("Error: on of the convertFileToAIFF" + " parameters is null!");
                return null;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(inFile);
            while ((b = ais.read()) != -1) {
                vector.add(new Integer(b));
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } catch (UnsupportedAudioFileException ex) {
            System.err.println(ex.toString());
        }
        return vector;
    }

    static void convertFileToAIFF(String inputPath, String outputPath) {
        AudioFileFormat inFileFormat;
        File inFile;
        File outFile;
        try {
            inFile = new File(inputPath);
            outFile = new File(outputPath);
        } catch (NullPointerException ex) {
            System.out.println("Error: on of the convertFileToAIFF" + " parameters is null!");
            return;
        }
        try {
            inFileFormat = AudioSystem.getAudioFileFormat(inFile);
            System.out.println(inFileFormat + "\n");
            if (inFileFormat.getType() != AudioFileFormat.Type.AIFF) {
                System.out.println("Check 00");
                AudioInputStream inFileAIS = AudioSystem.getAudioInputStream(inFile);
                AudioFileFormat.Type[] types = AudioSystem.getAudioFileTypes(inFileAIS);
                for (AudioFileFormat.Type t : types) {
                    System.out.println(t);
                }
                System.out.println("Check 01");
                //inFileAIS.reset();
                System.out.println("Check 02");
                if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AIFF, inFileAIS)) {
                    System.out.println("Conversion is possible!");
                    AudioSystem.write(inFileAIS, AudioFileFormat.Type.AIFF, outFile);
                    System.out.println("Successfully made AIFF file, "
                            + outFile.getPath() + ", from "
                            + inFileFormat.getType() + " file, "
                            + inFile.getPath() + ".");
                    inFileAIS.close();
                    return;
                } else {
                    System.out.println("Warning: AIFF conversion of "
                            + inFile.getPath()
                            + " is not currently supported by AudioSystem.");
                }
            } else {
                System.out.println("Input file " + inFile.getPath()
                        + " is AIFF." + " Conversion is unnecessary.");
            }
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Error: " + inFile.getPath()
                    + " is not a supported audio file type!");
            return;
        } catch (IOException ex) {
            System.out.println("Error: failure attempting to read " + inFile.getPath() + "!");
            return;
        }
    }

    public static int convertFrameSampleSizeToMilliSeconds(int sample_rate, int frame_sample_size) {
        return (int) (frame_sample_size * 1000.0) / sample_rate;
    }
    
    public static void applyOneDFTToSegments(String in_path, int frame_sample_size, int channelNum) {
        try {

            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            inWavFile.display();

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
            double normalizer = WavFileManip.convertFrameSampleSizeToSeconds((int) sampleRate, frame_sample_size);
            int currFrameSampleNum = 0;

                //currZCR = ZeroCrossingRate.computeZCR01(buffer[channel_num], normalizer);
                //currAmp = WavFileManip.computeAvrgAbsAmplitude(buffer[channel_num]);
            do {
                framesRead = inWavFile.readFrames(buffer, frame_sample_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                    Complex [] spectrum = OneDDFT.computeSpectrum(buffer[channelNum], -1, 0.001);
                    OneDDFT.displayMaxFreqMagn(spectrum);
                }

            } while (framesRead != 0);

            inWavFile.close();
            

        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    // follow applyOneDDFTtoSegments to implement a function that extracts coefficients
    // for specific frequencies in a frame.
    public static void searchFramesForFourierCoeffs(String in_path, int frame_sample_size, int channelNum, 
            int freq_range_start, int freq_range_end, double xlower, double xupper, double step) {
        try {

            // Open the wav file specified as the first argument
            WavFile inWavFile = WavFile.openWavFile(new File(in_path));

            // Display information about the wav file
            inWavFile.display();

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
            double normalizer = WavFileManip.convertFrameSampleSizeToSeconds((int) sampleRate, frame_sample_size);
            int currFrameSampleNum = 0;
            double norm_step = (xupper - xlower)/frame_sample_size;

                //currZCR = ZeroCrossingRate.computeZCR01(buffer[channel_num], normalizer);
                //currAmp = WavFileManip.computeAvrgAbsAmplitude(buffer[channel_num]);
            int count_frames = 0;
            do {
                framesRead = inWavFile.readFrames(buffer, frame_sample_size);
                if (framesRead > 0) {
                    currFrameSampleNum++;
                    //Complex [] spectrum = OneDDFT.computeSpectrum(buffer[channelNum], -1, 0.001);
                    //OneDDFT.displayMaxFreqMagn(spectrum);
                    
                    ArrayList<Harmonic> clist = FourierSeriesExpansion.expandFromFunctionData(buffer[channelNum], freq_range_start, freq_range_end, xlower, xupper, norm_step);
                   
                    Harmonic maxpair = FourierSeriesExpansion.findMaxCoeffPairAboveCoeffThresh(clist, 0.001);
                    if ( maxpair != null ) {
                         System.out.println(count_frames + ")***************");
                        System.out.println("MAX = " + maxpair.toString());
                    }
                    //else {
                    //    System.out.println("MAX = NULL");
                    //}
                    /*
                    for(Harmonic cp: clist) {
                        if ( cp.getAC() != 0 || cp.getAC() != 0 )
                            System.out.println(cp.toString());
                    }
                     * 
                     */
                    count_frames++;
                }

            } while (framesRead != 0);

            inWavFile.close();
        } catch (IOException | WavFileException e) {
            System.err.println(e);
        }
    }
    // the best result is chan_num=0, frame_size = 44100/2, -Math.PI, Math.PI, 0.022);
    public static void test4thOctaveDetect(String file_name, int chan_num, int[] frame_sizes, 
            double scaler, 
            double xlower, double xupper, double cth, double ath, double error_margin) {
        for(int fs: frame_sizes) {
            detect4thOctaveNotes(file_name, chan_num, fs, scaler, xlower, xupper, cth, ath, error_margin);
        }
    }
    
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
    
    
    
    
    //detectAllNotesByHarmonicCoeffMagnitude(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", 0, 44100/2, -Math.PI, Math.PI, 0.01, 0.01);
    //detectAllNotesByHarmonicAmplitude(AUDIO_DIR_02, "ODE_TO_JOY_RIGHT_HAND.wav", 0, 44100/2, -Math.PI, Math.PI, 0.01, 0.01);
        
    //detectAllNotesByHarmonicCoeffMagnitude(AUDIO_DIR_02, "ODE_TO_JOY_ORCHESTRA.wav", 0, 44100/2, -Math.PI, Math.PI, 0.01, 0.01);
    //detectAllNotesByHarmonicAmplitude(AUDIO_DIR_02, "ODE_TO_JOY_ORCHESTRA.wav", 0, 44100/2, -Math.PI, Math.PI, 0.01, 0.01);
    
    
    // I need to do the fourier analysis in Octave and then
    // try boosting the amplitude by some constant factor
    public static void main(String[] args) {        
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul, 44100);
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul, 44100/2);  
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul, 44100/4);
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul, 44100/8);
        
        AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_01_03_05jul, 44100/16);
         
         
        System.out.println("************");
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul, 44100);
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul, 44100/2);  
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul, 44100/4);
        
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul, 44100/8);
        
        AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_04_06_05jul, 44100/16);
        
        System.out.println("************");
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul, 44100);
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul, 44100/2);  
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul, 44100/4);
        
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul, 44100/8);
        AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul, 44100/16);
        
        System.out.println("************");
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul, 44100);
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul, 44100/2);  
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul, 44100/4);
        
        //AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul, 44100/8);
        AudioTrials.detectAllPiano88A440OctavesAtFreq(BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul, 44100/16);
         
       
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
    static void detectAllPiano88A440OctavesAtFreq(String dir, int freq) {   
        File folder = new File(dir);
        String freqStr = Integer.toString(freq);
        for(final File fileEntry: folder.listFiles()) {
            if ( !fileEntry.isDirectory() ) {
                System.out.println(fileEntry.toString() + " at " + freqStr);
                AudioTrials.detectAllPiano88A440Octaves(fileEntry.toString(), 
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
}
