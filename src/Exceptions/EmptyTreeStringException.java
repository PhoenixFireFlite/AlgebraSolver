package Exceptions;

public class EmptyTreeStringException extends Exception{

    public EmptyTreeStringException(){
        super("Tree string cannot be empty!");
    }

}
