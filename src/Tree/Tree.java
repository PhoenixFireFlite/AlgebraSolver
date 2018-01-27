package Tree;

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

    public Tree(String string){ setTree(string); }

    public void setTree(String string){
        if(string.length() > 0) {
            ArrayList<Token> tokens = RPN_Parser.toPostfix(RPN_Parser.tokenize(string));
            if (tokens.size() > 0) {
                Node node = RPN_Parser.assembleTree(tokens);
                if (node != null) {
                    rootnode = node;
                    node.setTree(this);
                }
            }
        }
    }

    public boolean matches(Tree tree){
        MatchResponse b = rootnode.matches(tree.rootnode);
        System.out.println(b.getMap());
        return b.getValidity();
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

    public void printTree(){
        if(rootnode != null)
            System.out.println(rootnode.getBranches());
    }

    public String toString(){
        if(rootnode != null)
            return rootnode.toString();
        return null;
    }

}