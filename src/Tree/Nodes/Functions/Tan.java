package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Tan extends Unary {

    public Tan(){}
    public Tan(Node node){
        super(node);
    }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimalMath.tan(value, CONSTANTS.TrigFunctionsMathContext);
    }

    public Fraction getFractionalValue(){
        return new Fraction(getDecimalValue());
    }

    public String toString(){
        return "Tan("+getChildren()[0]+")";
    }

    public Node getCopy() {
        return new Tan(getChildren()[0].getCopy());
    }

    public String getTypeString(){
        return "Tan";
    }

    public Type getType(){
        return Type.Tan;
    }
}
