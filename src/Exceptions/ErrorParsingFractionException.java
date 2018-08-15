package Exceptions;

public class ErrorParsingFractionException extends Exception{

    public ErrorParsingFractionException(String fractionToParse){
        super(String.format("Cannot parse %s", fractionToParse));
    }

}
