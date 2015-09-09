package org.vkedco.audio.event.detection.fourier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.vkedco.audio.music.piano88A440.octave0.ZerothOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave1.FirstOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave2.SecondOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave3.ThirdOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave4.FourthOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave5.FifthOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave6.SixthOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave7.SeventhOctaveFourierDetector;
import org.vkedco.audio.music.piano88A440.octave8.EightthOctaveFourierDetector;

/**
 *****************************
 * @author Vladimir Kulyukin
 *****************************
 */
public class Piano88A440OctaveFourierDetector {

    public static HashMap<Integer, ArrayList<String>> detectFourierKeysByCoeffMagnitude(String file, int octave_num, int channel_num, int frame_size,
            double scaler, double xlower, double xupper,
            double cth, double ath, double error_margin) {
        switch (octave_num) {
            case 0:
                return ZerothOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 1:
                return FirstOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 2:
                return SecondOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 3:
                return ThirdOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 4:
                return FourthOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 5:
                return FifthOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 6:
                return SixthOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 7:
                return SeventhOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 8:
                return EightthOctaveFourierDetector.detectFourierKeysByCoeffMagnitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            default:
                return null;
        }
    }

    public static HashMap<Integer, ArrayList<String>> detectFourierKeysByHarmonicAmplitude(String file, int octave_num, int channel_num, int frame_size,
            double scaler, double xlower, double xupper,
            double cth, double ath, double error_margin) {
        switch (octave_num) {
            case 0:
                return ZerothOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 1:
                return FirstOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 2:
                return SecondOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 3:
                return ThirdOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 4:
                return FourthOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 5:
                return FifthOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 6:
                return SixthOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 7:
                return SeventhOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            case 8:
                return EightthOctaveFourierDetector.detectFourierKeysByHarmonicAmplitude(file, channel_num, frame_size, scaler, xlower, xupper,
                        cth, ath, error_margin);
            default:
                return null;
        }
    }

    public static void displayHarmonicFrameMap(HashMap<Integer, ArrayList<String>> map) {
        if (map == null) {
            return;
        }
        for (Entry<Integer, ArrayList<String>> e : map.entrySet()) {
            if (e.getValue().size() > 0) {
                System.out.print("Frame " + e.getKey().toString() + " -- ");
                for (String s : e.getValue()) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        }
    }

    public static void displayAllHarmonicsPerFrame(HashMap<Integer, ArrayList<String>> map0,
            HashMap<Integer, ArrayList<String>> map1,
            HashMap<Integer, ArrayList<String>> map2,
            HashMap<Integer, ArrayList<String>> map3,
            HashMap<Integer, ArrayList<String>> map4,
            HashMap<Integer, ArrayList<String>> map5,
            HashMap<Integer, ArrayList<String>> map6,
            HashMap<Integer, ArrayList<String>> map7,
            HashMap<Integer, ArrayList<String>> map8,
            int frame_upper_limit) {

        for (int fn = 1; fn <= frame_upper_limit; fn++) {
            Integer key = new Integer(fn);
            if (map0 != null) {
                if (map0.containsKey(key) && map0.get(key).size() > 0) {
                    for (String note : map0.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map1 != null) {
                if (map1.containsKey(key) && map1.get(key).size() > 0) {
                    for (String note : map1.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }

            }
            if (map2 != null) {
                if (map2.containsKey(key) && map2.get(key).size() > 0) {
                    for (String note : map2.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map3 != null) {
                if (map3.containsKey(key) && map3.get(key).size() > 0) {
                    for (String note : map3.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map4 != null) {
                if (map4.containsKey(key) && map4.get(key).size() > 0) {
                    for (String note : map4.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map5 != null) {
                if (map5.containsKey(key) && map5.get(key).size() > 0) {
                    for (String note : map5.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map6 != null) {
                if (map6.containsKey(key) && map6.get(key).size() > 0) {
                    for (String note : map5.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map7 != null) {
                if (map7.containsKey(key) && map7.get(key).size() > 0) {
                    for (String note : map7.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
            if (map8 != null) {
                if (map8.containsKey(key) && map8.get(key).size() > 0) {
                    for (String note : map8.get(key)) {
                        System.out.print(note + "\t");
                    }
                    System.out.println();
                }
            }
        }
    }
}
