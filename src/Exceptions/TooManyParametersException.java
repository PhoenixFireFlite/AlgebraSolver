package Exceptions;

public class TooManyParametersException extends Exception{

    public TooManyParametersException(String stack){
        super(String.format("Stack finalized with: %s!", stack));
    }

}
