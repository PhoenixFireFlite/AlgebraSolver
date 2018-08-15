package Exceptions;

public class VariableNameTooLongException extends Exception{

    public VariableNameTooLongException(String name){
        super(String.format("Variable names should be a single char. Not %s!",name));
    }

}
