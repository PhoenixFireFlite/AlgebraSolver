package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Cot extends Unary {

    public Cot(){}
    public Cot(Node node){
        super(node);
    }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimalMath.cot(value, CONSTANTS.TrigFunctionsMathContext);
    }

    public Fraction getFractionalValue(){
        return new Fraction(getDecimalValue());
    }

    public String toString(){
        return "Cot("+getChildren()[0]+")";
    }

    public Node getEmptyCopy() {
        return new Cot();
    }

    public String getTypeString(){
        return "Cot";
    }

    public Type getType(){
        return Type.Cot;
    }
}
