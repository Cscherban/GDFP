package com.company;

/**
 * Parses the Fasta file using the GFF params as well as user specified qualifications
 * Created by Christopher on 1/2/2018.
 */
public class FASTAParserWithGFF {
    private FASTA FASTAData;
    private GFF GFFParams;

    public FASTAParserWithGFF(FASTA FASTAData, GFF GFFParams){
        this.FASTAData = FASTAData;
        this.GFFParams = GFFParams;
    }
    public String getAllGenes(){
        String accumulator = "";
        String fasta = FASTAData.getSequenceData();
        for(GFFRow r: GFFParams){
            accumulator += fasta.substring(r.start,r.end);
        }
        return accumulator;

    }



}
