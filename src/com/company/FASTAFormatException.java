package com.company;

/**
 * A Little Exception to the Rule
 * Created by Christopher on 1/2/2018.
 */
import java.io.IOException;

public class FASTAFormatException extends IOException {
    public FASTAFormatException(String message){
        super(message);
    }
}
