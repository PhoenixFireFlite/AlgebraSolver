package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Binary;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Root extends Binary {

    public Root(){}
    public Root(Node a, Node b){
        super(a,b);
    }

    public BigDecimal getDecimalValue() {
        Node[] children = getChildren();
        BigDecimal aVal = children[0].getDecimalValue(), bVal = children[1].getDecimalValue();
        if(aVal == null || bVal == null)
            return null;
        return BigDecimalMath.root(aVal,bVal,CONSTANTS.RoundingPlacesMathContext);
    }

    public Fraction getFractionalValue() {
        Node[] children = getChildren();
        Fraction aVal = children[0].getFractionalValue(), bVal = children[1].getFractionalValue();
        if(aVal == null || bVal == null)
            return null;
        return aVal.pow(bVal.reciprocal());
    }

    public String toString(){
        return "Root("+getChildren()[0]+","+getChildren()[1]+")";
    }

    public Node getEmptyCopy() {
        return new Root();
    }

    public String getTypeString(){
        return "Root";
    }

    public Type getType(){
        return Type.Root;
    }
}
