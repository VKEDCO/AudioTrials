package org.vkedco.audio.music.piano88A440;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.vkedco.audio.event.detection.BeePIPiano88A440NoteDetection;

/**
 *
 * @author Vladimir Kulyukin
 */
public class Piano88A440LogoMusic {
    
     final static String PLAY = "PLAY";
     final static String LB = "[";
     final static String RB = "]";
     final static String SP = " ";
     final static String I = "i";
     final static String T = "t";
     final static String V = "v";
     final static String NL = "\n";
     
     static String processNoteLine(String noteLine) {
         String[] notes = noteLine.split("\\s+");
         StringBuilder sb = new StringBuilder();
         System.out.println("" + notes.length);
         if ( notes.length == 1 ) {
             sb.append(PLAY);
             sb.append(SP);
             sb.append(LB);
             sb.append(notes[0]);
             sb.append(RB);
             sb.append(NL);
         }
         else {
             sb.append(PLAY);
             sb.append(SP);
             sb.append(LB);
             sb.append(LB);
             for(String note: notes) {
                 sb.append(note);
                 sb.append(SP);
             }
             sb.append(RB);
             sb.append(RB);
             sb.append(NL);
         }
         return sb.toString();
     }
     
     
     public static void keyNotesFileToLogoMusicFile(String notesFile, String logoMusicFile, 
                                    int instrument, int tempo, int volume) throws FileNotFoundException, IOException 
     {
        FileInputStream noteStream = new FileInputStream(notesFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(noteStream));
        FileWriter fw= new FileWriter(logoMusicFile); 
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(PLAY + SP + LB + I + instrument + SP + T + tempo + SP + V + volume + RB);
        bw.write(NL);

        String strLine;
        while (( strLine = br.readLine()) != null )   {
            System.out.println (strLine);
            String logoCommand = processNoteLine(strLine);
            System.out.println(logoCommand);
            bw.write(logoCommand);
        }

        //Close the input stream
        br.close();
        bw.flush();
        bw.close();
    }
     
     //ArrayList<HashMap<Integer, ArrayList<String>>>
     public static void octaveMapsToLogoMusicFile(ArrayList<HashMap<Integer, ArrayList<String>>> octaveMaps,
             String logoMusicFile, int instrument, int tempo, int volume, 
             int frameLimit) throws FileNotFoundException, IOException 
     {
        FileWriter fw= new FileWriter(logoMusicFile); 
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write(PLAY + SP + LB + I + instrument + SP + T + tempo + SP + V + volume + RB);
        bw.write(NL);
        String logoCommand = null;
        for(int frameNumber = 0; frameNumber <= frameLimit; frameNumber++) {
            logoCommand = frameToLogoPlayCommand(new Integer(frameNumber), octaveMaps);
            System.out.println(logoCommand);
            if ( logoCommand != null ) {
                bw.write(logoCommand);
                bw.write(NL);
            }
        }

        bw.flush();
        bw.close();
    }
     
    static String frameToLogoPlayCommand(Integer frameNumber, ArrayList<HashMap<Integer, ArrayList<String>>> octaveMaps) {
        StringBuilder notesBuffer = new StringBuilder();
        StringBuilder logoCommandBuffer = new StringBuilder();
        
        for(HashMap<Integer, ArrayList<String>> octaveMap : octaveMaps) {
            if ( octaveMap.containsKey(frameNumber) ) {
                for(String note: octaveMap.get(frameNumber)) {
                    notesBuffer.append(note).append(SP);
                }
            }
        }
        
        System.out.println(notesBuffer.toString());
        String notesStr = notesBuffer.toString();
        if ( notesStr.matches(".*\\w.*") ) {
        
            String notes[] = notesStr.split("\\s+");
            System.out.println("notes.length = " + notes.length);
       
            logoCommandBuffer.append(PLAY);
            logoCommandBuffer.append(SP);
        
            if ( notes.length == 1 ) {
                logoCommandBuffer.append(LB);
            }
            else {
                logoCommandBuffer.append(LB);
                logoCommandBuffer.append(LB);
            }
        
            for(String note: notes) {
                logoCommandBuffer.append(note);
                logoCommandBuffer.append(SP);
            }
        
            if ( notes.length == 1 ) {
                logoCommandBuffer.append(RB);
            }
            else {
                logoCommandBuffer.append(RB);
                logoCommandBuffer.append(RB);
            } 
        
            return logoCommandBuffer.toString();
        }
        return null;
    } 
    
    final static String DIR = "C:/Users/Vladimir/research/audio_files/wav/beepi/f02_usuof_25jul2015/";
    static String[] notePaths = {
        "BeeSymphony_13_15_05jul2015_44100_notes.txt",
        "BeeSymphony_13_15_05jul2015_22050_notes.txt",
        "BeeSymphony_13_15_05jul2015_11025_notes.txt",
        "BeeSymphony_13_15_05jul2015_5512_notes.txt",
        "BeeSymphony_13_15_05jul2015_2756_notes.txt",
        "BeeSymphony_16_18_05jul2015_44100_notes.txt",
        "BeeSymphony_16_18_05jul2015_22050_notes.txt",
        "BeeSymphony_16_18_05jul2015_11025_notes.txt",
        "BeeSymphony_16_18_05jul2015_5512_notes.txt",
        "BeeSymphony_16_18_05jul2015_2756_notes.txt"
    };
    
    static String[] logoPaths = {
        "BeeSymphony_13_15_05jul2015_44100_logo.txt",
        "BeeSymphony_13_15_05jul2015_22050_logo.txt",
        "BeeSymphony_13_15_05jul2015_11025_logo.txt",
        "BeeSymphony_13_15_05jul2015_5512_logo.txt",
        "BeeSymphony_13_15_05jul2015_2756_logo.txt",
        "BeeSymphony_16_18_05jul2015_44100_logo.txt",
        "BeeSymphony_16_18_05jul2015_22050_logo.txt",
        "BeeSymphony_16_18_05jul2015_11025_logo.txt",
        "BeeSymphony_16_18_05jul2015_5512_logo.txt",
        "BeeSymphony_16_18_05jul2015_2756_logo.txt"
    };
    
    public static void audioFileToLogoCommands(String file_path, String logo_file, 
            int instrument, int tempo, int volume, // these are logo parameters
            int channel_num, int frame_size, // channel number & frame_size
            double scaler, double xlower, double xupper, 
            double cth, double ath, double error_margin,
            int frameLimit) throws FileNotFoundException, IOException 
    {
        ArrayList<HashMap<Integer, ArrayList<String>>> octaveMaps = 
                BeePIPiano88A440NoteDetection.mapAllPiano88A440Octaves(file_path, channel_num, frame_size, 
                    scaler, xlower, xupper, cth, ath, error_margin);
        octaveMapsToLogoMusicFile(octaveMaps, logo_file, instrument, tempo, volume, frameLimit);   
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
    
    final static String LOGO_FILE_EXTENSION    = "_logo.txt";
    final static String WAV_FILE_EXTENSION     = ".wav";
    static String convertAudioPathToLogoPath(String audioPath, int frame_size) {
        System.out.println("audioPath = " + audioPath);
        String logoPath = audioPath.substring(0, audioPath.indexOf(WAV_FILE_EXTENSION)) 
                + "_" + frame_size + LOGO_FILE_EXTENSION;
        System.out.println("logoPath = " + logoPath);
        return logoPath;
    }
    
    static boolean isWavFile(String audioPath) {
        return audioPath.endsWith(WAV_FILE_EXTENSION);
    }
    
    // freq = 44100, 22050, 11025, 5512, 2756
    static void generatePiano88A440LogoFilesAtFreqForDir(String dir, int frame_size) throws FileNotFoundException, IOException {   
        if ( dir == null ) return;
        File folder = new File(dir);
        String freqStr = Integer.toString(frame_size);
        for(final File fileEntry: folder.listFiles()) {
            if ( fileEntry.isDirectory() ) {
                generatePiano88A440LogoFilesAtFreqForDir(fileEntry.toString(), frame_size);
            }
            else if ( !fileEntry.isDirectory() ) {
                System.out.println(fileEntry.toString() + " at " + freqStr);
                String audioPath = fileEntry.toString();
                if ( !isWavFile(audioPath) ) continue;
                String logoPath = convertAudioPathToLogoPath(audioPath, frame_size);
                audioFileToLogoCommands(audioPath, logoPath,
                    48, 333, 127,   // instrument, tempo, volume
                    0, frame_size,  // channel num, frame size
                    0,              // scaler
                    -Math.PI,       // xlower
                    Math.PI,        // xupper
                    0.001,          // cth
                    0.001,          // ath
                    0.5,            // error_margin
                    2000            // frame_limit
                );
                System.out.println("=============");
            }
        }
    }
    
    static void processBeePiAudioDirAtAllFreqs() throws FileNotFoundException, IOException {
        /*
         * 
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_22_00_04jul_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_07_09_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_10_12_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_13_15_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_16_18_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_19_21_05jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_22_00_05jul_06jul);
        
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_01_03_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_04_06_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_07_09_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_10_12_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_13_15_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_16_18_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_17_21_06jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_22_00_06jul_07jul);
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_22_00_04jul_05jul);
         * 
         */
        
        
        generatePiano88A440LogoFilesAtAllFreqsForDir(BeePIPiano88A440NoteDetection.BUZZ_DIR_F02_USOF_25Jul2015_19_21_04jul);
        
        
    }
    
    static void generatePiano88A440LogoFilesAtAllFreqsForDir(String dir) throws FileNotFoundException, IOException { 
       
        generatePiano88A440LogoFilesAtFreqForDir(
                dir,
                BeePIPiano88A440NoteDetection.FREQ_44100
                );
        
        generatePiano88A440LogoFilesAtFreqForDir(
                dir,
                BeePIPiano88A440NoteDetection.FREQ_22050
                );
          
        generatePiano88A440LogoFilesAtFreqForDir(
                dir,
                BeePIPiano88A440NoteDetection.FREQ_11025
                );
          
        generatePiano88A440LogoFilesAtFreqForDir(
                dir,
                BeePIPiano88A440NoteDetection.FREQ_5512
                );
        
        generatePiano88A440LogoFilesAtFreqForDir(
                dir,
                BeePIPiano88A440NoteDetection.FREQ_2756
                ); 
    }
     
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        processBeePiAudioDirAtAllFreqs();
    }
    
}
