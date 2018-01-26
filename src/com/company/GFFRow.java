package com.company;


public enum strand_direction{
FORWARD,REVERSE, UNKNOWN
}


/**
 * Class to represent a row of a GFF file
 * Created by Christopher on 1/2/2018.
 */
public class GFFRow {

    public String sequence;
    public String source;
    public String feature;
    public int start;
    public int end;
    public int score;
    public strand_direction strand;
    public char frame;
    public char phase;
    public String attributes;
    public boolean isGFF3;

    public GFFRow(String row, boolean isGFF3){
        this.isGFF3 = isGFF3;
        parseRow(row);
    }

    public GFFRow(String row){
        this(row,true);
    }

    /**
     * @param row, a string from a row of a GFF file
     */
    private void parseRow(String row){
        //Split the row along any whitespace
        String cols[] = row.split("\\t+");


        if(cols.length != 9){
            //if the row doens't have 9 cols then it isnt in GFF, dont asign anythign
            System.err.println("This is not Proper GFF format");
            return;
        }

        //asign all the values based on the GFF structure
        sequence = cols[0];
        source = cols[1];
        feature = cols[2];
        start = Integer.parseInt(cols[3]);
        end = Integer.parseInt(cols[4]);


        if(!cols[5].equals(".")){
            score = Integer.parseInt(cols[5]);
        }else{
            score = -1;
        }
        if(cols[6].equals("+")){
            strand = strand_direction.FORWARD;
        } else if(cols[6].equals("-")){
            strand = strand_direction.REVERSE;
        } else {
            strand = strand_direction.UNKNOWN;
        }



        if(isGFF3){
            //if it is GFF3 format, use phase variable
            phase = cols[7].charAt(0);
        }else{
            //if it isnt GFF3 format, use the frame, as is required.
            frame = cols[7].charAt(0);
        }

        //puts everythign else into attributes
        attributes = cols[8];

    }


}
