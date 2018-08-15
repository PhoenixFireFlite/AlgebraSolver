package Exceptions;

public class UnknownFunctionException extends Exception{

    public UnknownFunctionException(String function){
        super(String.format("%s is not a function!", function));
    }

}
