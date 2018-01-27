package Tree.Nodes.Functions;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;
import Tree.Nodes.Primitives.Unary;

import java.math.BigDecimal;

public class Sin extends Unary {

    public Sin(){}
    public Sin(Node node){
        super(node);
    }

    public BigDecimal getDecimalValue() {
        BigDecimal value = getChildren()[0].getDecimalValue();
        if(value == null)
            return null;
        return BigDecimalMath.sin(value, CONSTANTS.TrigFunctionsMathContext);
    }

    public String toString(){ return "Sin("+getChildren()[0]+")"; }

    public Fraction getFractionalValue(){
        return new Fraction(getDecimalValue());
    }

    public Node getCopy() {
        return new Sin(getChildren()[0].getCopy());
    }

    public String getTypeString(){
        return "Sin";
    }

    public Type getType(){
        return Type.Sin;
    }
}
