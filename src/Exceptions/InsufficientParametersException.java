package Exceptions;

public class InsufficientParametersException extends Exception{

    public InsufficientParametersException(String functionName, int parametersRequired){
        super(String.format("%s requires %d parameters!", functionName, parametersRequired));
    }

}
