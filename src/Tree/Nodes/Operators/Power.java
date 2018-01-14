package Tree.Nodes.Operators;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Binary;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.NodeType;


import java.math.BigDecimal;

public class Power extends Binary {

    public Power(){}
    public Power(Node a, Node b){
        super(a, b);
    }

    public BigDecimal getDecimalValue() {
        Node[] children = getChildren();
        BigDecimal aVal = children[0].getDecimalValue(), bVal = children[1].getDecimalValue();
        if(aVal == null || bVal == null)
            return null;
        return BigDecimalMath.pow(aVal, bVal, CONSTANTS.RoundingPlacesMathContext);
    }

    public Fraction getFractionalValue() {
        Node[] children = getChildren();
        Fraction aVal = children[0].getFractionalValue(), bVal = children[1].getFractionalValue();
        if(aVal == null || bVal == null)
            return null;
        return aVal.pow(bVal);
    }

    @Override
    public String toString(){
        int prec = getPrecedence("^");

        Node[] children = getChildren();

        String a = (getNodePrecedence(children[0]) <= prec)
                ? "("+children[0].toString()+")" : children[0].toString();

        String b = (getNodePrecedence(children[1]) <= prec && children[1].getType() != getType())
                ? "("+children[1].toString()+")" : children[1].toString();

        return a+"^"+b;
    }

    public Node getEmptyCopy() {
        return new Power();
    }

    public String getTypeString(){
        return "^";
    }

    public Type getType(){
        return Type.Power;
    }
}
