package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Sqrt extends Unary {

    private static Fraction HALF = new Fraction(1,2);

    public Sqrt(){}
    public Sqrt(Node node){ super(node); }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimalMath.sqrt(value, CONSTANTS.TrigFunctionsMathContext);
    }

    public Fraction getFractionalValue() {
        Fraction value = getChildren()[0].getFractionalValue();
        if(value == null)
            return null;
        return value.pow(HALF);
    }

    public String toString(){
        return "Sqrt("+getChildren()[0]+")";
    }

    public Node getEmptyCopy() {
        return new Sqrt();
    }

    public String getTypeString(){
        return "Sqrt";
    }

    public Type getType(){
        return Type.Sqrt;
    }
}
