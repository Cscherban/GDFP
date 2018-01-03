package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This Object is a Bundling of Rows from the GFF file.
 * Created by Christopher on 1/2/2018.
 */
public class GFF implements Iterable<GFFRow>{
    private ArrayList<GFFRow> backingStructure;
    private int size = 0;


    /**
     * Constructor
     * @param source file for GFF data
     */
    public GFF(String source){
        setContents(source);
        size = backingStructure.size();
    }

    /**
     * Sets the backing data structure
     * @param source the fine from which it is gotten
     */
    private void setContents(String source){
        File file = new File(source);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                if (st.charAt(0) != '#') {
                    backingStructure.add(new GFFRow(st));
                }
            }
        }
        catch(IOException e ){
            System.err.println("WOOPS JAVA IOException:" + e.getMessage());
            /**
             * Do something in this case
             *
             */
        }
    }

    /**
     *
     * @param n
     * @return the nth row, with indexing starting at 0
     */
    public GFFRow getRow(int n){
        return backingStructure.get(n);
    }

    /**
     * Get size of backing array
     * @return Size of Backing array
     */
    public int getSize(){
        return size;
    }

    /**
     * Gets an iterator over the rows of the GFF file
     * @return an Iterator<GFFRow;
     */
    @Override
    public Iterator<GFFRow> iterator() {
        return new Iterator<GFFRow>() {
            int n = 0;
            @Override
            public boolean hasNext() {
                return n < getSize();
            }

            @Override
            public GFFRow next() {
                return getRow(n++);
            }
        };
    }
}
