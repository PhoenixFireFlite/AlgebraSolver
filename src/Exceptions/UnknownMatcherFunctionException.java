package Exceptions;

public class UnknownMatcherFunctionException extends Exception{

    public UnknownMatcherFunctionException(String functionName){
        super(String.format("%s is not a matcher function!", functionName));
    }

}
