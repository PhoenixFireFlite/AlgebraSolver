package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Csc extends Unary {

    public Csc(){}
    public Csc(Node node){
        super(node);
    }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimal.ONE.divide(BigDecimalMath.sin(value, CONSTANTS.TrigFunctionsMathContext), CONSTANTS.BigDecimalRoundingPlaces, CONSTANTS.BigDecimalRoundingMethod);
    }

    public Fraction getFractionalValue(){
        return new Fraction(getDecimalValue());
    }

    public String toString(){
        return "Csc("+getChildren()[0]+")";
    }

    public Node getCopy() {
        return new Csc(getChildren()[0].getCopy());
    }

    public String getTypeString(){
        return "Csc";
    }

    public Type getType(){
        return Type.Csc;
    }
}
