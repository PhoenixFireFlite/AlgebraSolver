package Parsing;

public class Token{

    public static enum Type{
        NUMBER,
        CONSTANT,
        OPERATOR,
        VARIABLE,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS,
        FUNCTION,
        COMMA
    }

    private String string;
    private Type type;

    public Token(String string, Type type){
        this.string = string;
        this.type = type;
    }

    public String getString(){
        return string;
    }

    public Type getType(){
        return type;
    }

    public String toString(){
        return getString();
    }

}
