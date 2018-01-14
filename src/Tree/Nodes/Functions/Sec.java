package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Sec extends Unary {

    public Sec(){}
    public Sec(Node node){
        super(node);
    }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimal.ONE.divide(BigDecimalMath.cos(value, CONSTANTS.TrigFunctionsMathContext), CONSTANTS.BigDecimalRoundingPlaces, CONSTANTS.BigDecimalRoundingMethod);
    }

    public Fraction getFractionalValue(){
        return new Fraction(getDecimalValue());
    }

    public String toString(){
        return "Sec("+getChildren()[0]+")";
    }

    public Node getEmptyCopy() {
        return new Sec();
    }

    public String getTypeString(){
        return "Sec";
    }

    public Type getType(){
        return Type.Sec;
    }
}
