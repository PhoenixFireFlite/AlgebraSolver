package Exceptions;

public class EmptyExpressionException extends Exception{

    public EmptyExpressionException(){
        super("Expression stack is empty!");
    }

}
