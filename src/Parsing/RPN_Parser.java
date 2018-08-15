package Parsing;

import Exceptions.*;
import Tree.Nodes.Functions.*;
import Tree.Nodes.Matchers.Any;
import Tree.Nodes.Operands.Constants.E;
import Tree.Nodes.Operands.Constants.I;
import Tree.Nodes.Operands.Constants.Pi;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Operands.Variable;
import Tree.Nodes.Operators.Add;
import Tree.Nodes.Operators.Divide;
import Tree.Nodes.Operators.Multiply;
import Tree.Nodes.Operators.Power;
import Tree.Nodes.Primitives.Node;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import static Parsing.Token.Type.*;

public class RPN_Parser {

    private static String[] functions = new String[]{
            "sin","cos","tan","csc","sec","cot","sqrt","root",
            ":any"};
    private static String[] constants = new String[]{"pi","e","i"};

    private static int getPrecedence(Token t){
        return Node.getPrecedence(t.getString());
    }

    private static int getAssociativity(Token t){
        if(t.getString().equals("^"))
            return 1;
        else
            return -1;
    }

    public static ArrayList<Token> tokenize(String expression){
        ArrayList<Token> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        Parsing.Token.Type dex = OPERATOR;

        for(int i=0;i<expression.length();i++){
            char c = expression.charAt(i);

            while(c == ' ') {c = expression.charAt(++i);}

            if(Character.isDigit(c) || c == '.'){ // NUMBER
                if(dex != NUMBER) {
                    tokens.add(new Token(builder.toString(), dex));
                    builder.setLength(0);
                }

                dex = NUMBER;
            } else if(Character.isLetter(c) || c == ':'){ // VARIABLE
                if(builder.toString().equals("-") && dex == NUMBER)
                    builder.append("1");

                if(dex != VARIABLE) {
                    tokens.add(new Token(builder.toString(), dex));
                    builder.setLength(0);
                }

                String varMash = builder.toString();
                boolean found = false;
                for(String s: constants){
                    if(s.equals(varMash+c)){
                        found = true;
                        break;
                    } else if(s.equals((varMash+c).toLowerCase())){
                        builder = new StringBuilder(builder.toString().toLowerCase());
                        c = Character.toLowerCase(c);
                        found = true;
                        break;
                    }
                }

                if(dex == NUMBER || dex == CONSTANT)
                    tokens.add(new Token("*", OPERATOR));

                if(found)
                    dex = CONSTANT;
                else
                    dex = VARIABLE;
            } else if (c == '(') { // LEFT_PARENTHESIS
                if(builder.toString().equals("-") && dex == NUMBER)
                    builder.append("1");

                if(dex == VARIABLE) {
                    String variableMash = builder.toString().toLowerCase();
                    int j = 0;
                    for(;j<functions.length;j++){
                        if(variableMash.endsWith(functions[j])) break;
                    }
                    if(j == functions.length){ // Not Function
                        for (char var : variableMash.toCharArray()) {
                            tokens.add(new Token(Character.toString(var), VARIABLE));
                            tokens.add(new Token("*", OPERATOR));
                        }
                        tokens.remove(tokens.size() - 1);
                    }else{ // Is Function
                        if(variableMash.length()-functions[j].length() != 0){
                            String vars = variableMash.substring(0, variableMash.length()-functions[j].length());
                            for(char var: vars.toCharArray()){
                                tokens.add(new Token(Character.toString(var), VARIABLE));
                                tokens.add(new Token("*", OPERATOR));
                            }
                        }
                        if(variableMash.charAt(0) == ':')
                            tokens.add(new Token(functions[j].substring(1), MATCHER));
                        else
                            tokens.add(new Token(functions[j], FUNCTION));
                        dex = FUNCTION;
                    }
                }else{
                    tokens.add(new Token(builder.toString(), dex));
                }
                builder.setLength(0);

                if(dex == NUMBER || dex == VARIABLE || dex == CONSTANT || dex == RIGHT_PARENTHESIS)
                    tokens.add(new Token("*", OPERATOR));

                dex = LEFT_PARENTHESIS;
            } else if(c == ')') { // RIGHT_PARENTHESIS
                if(dex == VARIABLE){
                    String variableMash = builder.toString();
                    for (char var : variableMash.toCharArray()) {
                        tokens.add(new Token(Character.toString(var), VARIABLE));
                        tokens.add(new Token("*", OPERATOR));
                    }
                    tokens.remove(tokens.size() - 1);
                }else {
                    tokens.add(new Token(builder.toString(), dex));
                }
                builder.setLength(0);

                dex = RIGHT_PARENTHESIS;
            } else { // OPERATOR
                if (dex != OPERATOR) {
                    if(dex == VARIABLE){
                        String variableMash = builder.toString();
                        for (char var : variableMash.toCharArray()) {
                            tokens.add(new Token(Character.toString(var), VARIABLE));
                            tokens.add(new Token("*", OPERATOR));
                        }
                        tokens.remove(tokens.size() - 1);
                    }else {
                        tokens.add(new Token(builder.toString(), dex));
                    }
                    builder.setLength(0);

                    if(c == ',')
                        dex = COMMA;
                    else
                        dex = (dex == LEFT_PARENTHESIS) ? NUMBER : OPERATOR;
                }else if(c == '-'){
                    tokens.add(new Token(builder.toString(), dex));
                    builder.setLength(0);

                    dex = NUMBER;
                }
            }

            builder.append(c);
        }

        if(tokens.size() > 0)
            tokens.remove(0);

        if(dex == VARIABLE){
            String variableMash = builder.toString();
            for (char var : variableMash.toCharArray()) {
                tokens.add(new Token(Character.toString(var), VARIABLE));
                tokens.add(new Token("*", OPERATOR));
            }
            tokens.remove(tokens.size() - 1);
        }else {
            tokens.add(new Token(builder.toString(), dex));
        }

        return tokens;
    }

    public static ArrayList<Token> toPostfix(ArrayList<Token> tokens){
        Stack<Token> stack = new Stack<>();
        ArrayList<Token> queue = new ArrayList<>();

        try {
            for (Token t : tokens) {
                switch (t.getType()) {
                    case VARIABLE:
                    case CONSTANT:
                    case NUMBER:
                        queue.add(t);
                        break;
                    case OPERATOR:
                        while (!stack.isEmpty() && (((getPrecedence(stack.peek()) > getPrecedence(t)) && getAssociativity(t) == 1) ||
                                ((getPrecedence(stack.peek()) >= getPrecedence(t)) && getAssociativity(t) == -1))) {
                            queue.add(stack.pop());
                        }
                        stack.push(t);
                        break;
                    case MATCHER:
                    case FUNCTION:
                    case LEFT_PARENTHESIS:
                        stack.push(t);
                        break;
                    case RIGHT_PARENTHESIS:
                        while (stack.peek().getType() != LEFT_PARENTHESIS)
                            queue.add(stack.pop());

                        stack.pop();

                        if (!stack.isEmpty() && (stack.peek().getType() == FUNCTION || stack.peek().getType() == MATCHER))
                            queue.add(stack.pop());

                        break;
                    case COMMA:
                        while (stack.peek().getType() != COMMA &&
                                stack.peek().getType() != LEFT_PARENTHESIS)
                            queue.add(stack.pop());

                        if (stack.peek().getType() == COMMA)
                            stack.pop();

                        break;
                    default:
                        System.err.println("ERROR: " + t.getString()+" ["+t.getType()+"]");
                        break;
                }
            }

            while (!stack.isEmpty())
                queue.add(stack.pop());

        }catch (EmptyStackException x){
            System.err.println("Expression format exception: 1");
        }

        return queue;
    }

    public static Node assembleTree(ArrayList<Token> tokens) throws Exception{
        Stack<Node> stack = new Stack<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case VARIABLE:
                    if(token.toString().length() > 1)
                        throw new VariableNameTooLongException(token.toString());
                    stack.push(new Variable(token.toString().charAt(0)));
                    break;
                case NUMBER:
                    try {
                        stack.push(new Fraction(token.toString()));
                    } catch (Exception x) {
                        throw new ErrorParsingFractionException(token.toString());
                    }
                    break;
                case CONSTANT:
                    switch (token.getString()){
                        case "pi": stack.push(new Pi()); break;
                        case "e":  stack.push(new E()); break;
                        case "i":  stack.push(new I()); break;
                    }
                case OPERATOR:
                    Node a, b;
                    try {
                        switch (token.getString()) {
                            case "+":
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Add(a, b));
                                break;
                            case "-":
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Add(a, new Multiply(new Fraction(-1), b)));
                                break;
                            case "*":
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Multiply(a, b));
                                break;
                            case "/":
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Divide(a, b));
                                break;
                            case "^":
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Power(a, b));
                                break;
                        }
                    }catch(EmptyStackException e){
                        throw new InsufficientParametersException(token.getString(), 2);
                    }
                    break;
                case MATCHER:
                    switch (token.getString()) {
                        case "any":
                            a = stack.pop();
                            if(a.toString().length() > 1)
                                throw new VariableNameTooLongException(token.toString());
                            stack.push(new Any(a.toString().charAt(0)));
                            break;
                        default: throw new UnknownMatcherFunctionException(token.getString());
                    }
                    break;
                case FUNCTION:
                    try{
                        switch (token.getString()) {
                            case "sin":
                                stack.push(new Sin(stack.pop()));
                                break;
                            case "cos":
                                stack.push(new Cos(stack.pop()));
                                break;
                            case "tan":
                                stack.push(new Tan(stack.pop()));
                                break;
                            case "csc":
                                stack.push(new Csc(stack.pop()));
                                break;
                            case "sec":
                                stack.push(new Sec(stack.pop()));
                                break;
                            case "cot":
                                stack.push(new Cot(stack.pop()));
                                break;
                            case "sqrt":
                                stack.push(new Sqrt(stack.pop()));
                                break;
                            case "root":
                                if(stack.size() < 2) throw new InsufficientParametersException("root", 2);
                                b = stack.pop();
                                a = stack.pop();
                                stack.push(new Root(a, b));
                                break;
                            default: throw new UnknownFunctionException(token.getString());
                        }
                    }catch(EmptyStackException e){
                        throw new InsufficientParametersException(token.getString(), 1);
                    }
                    break;
                default:
                    throw new UnknownTokenTypeException();
            }
        }

        if(stack.size() > 1)
            throw new TooManyParametersException(stack.toString());
        else if(stack.size() < 1)
            throw new EmptyExpressionException();

        return stack.pop();
    }

}
