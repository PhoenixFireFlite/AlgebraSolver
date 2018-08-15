package Tree;

import Exceptions.EmptyTreeStringException;
import Parsing.RPN_Parser;
import Parsing.Token;
import Simplifying.MatchResponse;
import Tree.Nodes.*;
import Tree.Nodes.Operands.*;
import Tree.Nodes.Primitives.Node;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Tree {

    private Node rootnode;

    public Tree(String string){
        try{
            setTree(string);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTree(String string) throws Exception{
        if(string.length() > 0) {
            ArrayList<Token> tokens = RPN_Parser.toPostfix(RPN_Parser.tokenize(string));
            if (tokens.size() > 0) {
                Node node = RPN_Parser.assembleTree(tokens);
                if (node != null) {
                    rootnode = node;
                    node.setTree(this);
                }
            }
        }else{
            throw new EmptyTreeStringException();
        }
    }

    public boolean matches(Tree tree){
        MatchResponse b = rootnode.matches(tree.rootnode);

        System.out.println("\n"+((b.checkValidity())?"Match Found":"No Matches Found"));
        System.out.println("Variable Possibilities: "+b.getMap()+"\n");

        boolean good = b.hasPossibleSolution();

        if(!good){
            System.out.println("FAIL FAIL FAIL");
        }

        return b.checkValidity();
    }

    public BigDecimal getDecimalValue(){
        if(rootnode == null)
            return null;
        BigDecimal value = rootnode.getDecimalValue();
        if(value == null)
            return null;
        return value.setScale(CONSTANTS.BigDecimalRoundingPlaces-1, CONSTANTS.BigDecimalRoundingMethod).stripTrailingZeros();
    }

    public Node getRoot(){
        return rootnode;
    }

    public void setRoot(Node node){
        rootnode = node;
    }

    public String getDecimalValueString(){
        BigDecimal value = getDecimalValue();
        if(value == null)
            return null;
        return value.toPlainString();
    }

    public Fraction getFractionalValue(){
        if(rootnode == null)
            return null;
        Fraction value = rootnode.getFractionalValue();
        if(value == null)
            return null;
        return value;
    }

    public String getFractionalValueString(){
        Fraction value = getFractionalValue();
        if(value == null)
            return null;
        return value.toString();
    }

    public boolean equalTo(Tree tree){
        return rootnode.equalTo(tree.rootnode);
    }

    public void printTree(){
        if(rootnode != null)
            System.out.println(rootnode.getBranches());
    }

    public void printInfix(){
        System.out.println(toString());
    }

    public String toString(){
        if(rootnode != null)
            return rootnode.toString();
        return null;
    }

}