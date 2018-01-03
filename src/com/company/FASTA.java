package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Christopher on 1/2/2018.
 */
public class FASTA {
    private boolean PAIRED_WITH_GFF = false;
    private String source;
    private String description;
    private String sequenceData;
    //Add a thing to hold Comments in the object, when moving to display purposes

    /**
     * Takes in a source file path and extracts needed data
     * @param source_file where the FASTA is located
     */
    public FASTA (String source_file){
        String data[] = extract(source_file);
        source = source_file;
        description = data[0];
        sequenceData = data[1];
    }

    /**
     *  No data extrection used. Useful when you have multisequence fasta files.
     * @param description
     * @param sequenceData
     */
    public FASTA(String description,String sequenceData ){
        this.description = description;
        this.sequenceData = sequenceData;
    }

    /**
     * @return seqeuce contained in file
     */
    public String getSequenceData(){
        return sequenceData;
    }

    /**
     *
     * @return the description in the file
     */
    public String getDescription(){
        return description;
    }

    /**
     * @return Whether or not there is a corresponding GFF
     */
    public boolean getPAIRED_WITH_GFF(){
        return PAIRED_WITH_GFF;
    }

    /**
     * Rechecks if there is a corresponding GFF
     */
    public void checkPAIRED_WITH_GFF(){
        PAIRED_WITH_GFF = checkGFF();
    }

    /**
     *
     * @return Whether or no there is a GFF file that exists
     */
    private boolean checkGFF(){
        if(source != null){
            return false;
        }
        return (new File(source.substring(0,source.length() - 6) + ".gff")).exists();

    }

    /**
     *
     * @param source_file Extracts data from source
     * @return an Array with Description and Sequence data in it
     */
    private String[] extract(String source_file){
        String contents = readFile(source_file);

        if(contents == null){
            System.out.println("Please Use a Conforming/Existing File:");
            /*
             Code to read from console input
             */
            // for now:
            System.exit(1);

        }
        String data[] = new String[2];
        int temp = contents.indexOf(">") + 1;
        int temp2 = contents.indexOf("\n",temp);
        data[0] = contents.substring(temp,temp2);
        data[1] = contents.substring(temp2);

        return data;
    }

    /**
     *
     * @param source read contents of a specified file.
     * @return Contents of the file
     */
    private String readFile(String source){
        int numGreaterThanSigns = 0;
        Path file = Paths.get(source);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.contains(">")){
                    if(++numGreaterThanSigns > 1){
                        throw new FASTAFormatException("Expected Single Sequence File");
                    }
                }
                sb.append(line);
            }
        }
        catch(FASTAFormatException f){
            System.err.format("FASTAFormatException, Unexpected Fasta Contents: %s%n", f);
            return null;
        }
        catch (FileNotFoundException e){
            System.err.format("File Not Found: %s%n", e);
            return null;
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
            return null;
        }
        return sb.toString();
    }
}
