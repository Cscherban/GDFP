package com.company;

/**
 * Parses the Fasta file using the GFF params as well as user specified qualifications
 * Created by Christopher on 1/2/2018.
 */
public class FASTAParserWithGFF {
    private FASTA FASTAData;
    private GFF GFFParams;

    /**
     *
     * @param FASTAData - the entirety of a FASTA file
     * @param GFFParams - the parsed and formated data of a GFF file
     */
    public FASTAParserWithGFF(FASTA FASTAData, GFF GFFParams){
        this.FASTAData = FASTAData;
        this.GFFParams = GFFParams;
    }

    /**
     *
     * @param nucleotides - a stream of nucleotides
     * @return the reverse complement of the nucleotide sequence
     * passed in
     */
    private String reverseComplement(String nucleotides){
        String result = "";

        char c
        for(int i = nucleotides.length() - 1; i > 0; i--){
            c = nucleotides.charAt(i);
            if(c == 'A')
                result += 'T';
            else if (c == 'T')
                result += 'A';
            else if (c == 'G')
                result += 'C';
            else if (c == 'C')
                result += 'G';
            else
                result += c;
        }
        return result;
    }


    /**
     *
     * @param filter - a function to determine whether or not
     *               A gene will be gotten/used by the querryer
     * @return - the set of desired genes, in string format, delminted by a \n
     *          Very easily used(can be split by '\n' delimiter or whatnot
     */

    public String getGenes(Predicate<GFFRow> filter){
        String accumulator = "";
        String fasta = FASTAData.getSequenceData();
        for(GFFRow r: GFFParams){
            if(filter.test(r)){
                if(r.strand == strand_direction.FORWARD){
                    accumulator += fasta.substring(r.start,r.end) + "\n";
                } else if (r.strand == strand_direction.REVERSE){
                    accumulator += reverseComplement(fasta.substring(r.start,r.end)) + "\n";
                }
            }

        }
        return accumulator;
    }

    /**
     *
     * @return all the genes
     */
    public String getAllGenes(){
        getGenes(true);
    }


    /**
     *
     * @param numBasePairs - the number of desired base pairs
     * @param filter - a function to filter results/values
     * @return an string with all the upsteram regions delimited by a '\n'
     */
    public String getUpstreamRegions(int numBasePairs, Predicate<GFFRow> filter){
        String accumulator = "";
        for(GFFRow r : GFFParams){
            if(filter.test(r)){
                if(r.strand == strand_direction.FORWARD){
                    accumulator += fasta.substring(r.start - numBasePairs,r.start);
                } else if (r.strand == strand_direction.REVERSE){
                    accumulator += reverseComplement(fasta.substring(r.end,r.end + numBasePairs));
                }
                accumulator += "\n";
            }
        }

    }

    /**
     *
     * @param numBasePairs - number of base pairs desired
     * @return all of the upstream regions
     */
    public String getUpstreamRegions(int numBasePairs){
        return getUpstreamRegions(numBasePairs,true);
    }





}
