package Tree.Nodes.Operators;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Binary;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.CONSTANTS;
import Tree.Nodes.NodeType;

import java.math.BigDecimal;

public class Divide extends Binary {

    public Divide(){}
    public Divide(Node a, Node b){
        super(a, b);
    }

    public BigDecimal getDecimalValue() {
        Node[] children = getChildren();
        BigDecimal aVal = children[0].getDecimalValue(), bVal = children[1].getDecimalValue();
        if(aVal == null || bVal == null)
            return null;
        return aVal.divide(bVal, CONSTANTS.BigDecimalRoundingPlaces, CONSTANTS.BigDecimalRoundingMethod);
    }

    public Fraction getFractionalValue() {
        Node[] children = getChildren();
        Fraction aVal = children[0].getFractionalValue(), bVal = children[1].getFractionalValue();
        if(aVal == null || bVal == null)
            return null;
        return aVal.divide(bVal);
    }

    public Node getEmptyCopy() {
        return new Divide();
    }

    public String getTypeString(){
        return "/";
    }

    public Type getType(){
        return Type.Divide;
    }
}
