package Tree.Nodes.Operands.Constants;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Pi extends Leaf {

    private static BigDecimal Pi_value = BigDecimalMath.pi(CONSTANTS.MathConstantsMathContext);
    private static Fraction Pi_fraction = new Fraction(Pi_value);

    public Pi(){ }

    @Override
    public int sameTypeCompare(Node node) { return 0; }

    @Override
    public boolean sameTypeEquals(Node node) { return true; }

    public String getTypeString(){
        return "Pi";
    }

    public BigDecimal getDecimalValue() { return Pi_value; }

    public Fraction getFractionalValue() { return Pi_fraction; }

    public Node getCopy(){
        return new Pi();
    }

    public Type getType(){
        return Type.Pi;
    }

    public String toString(){ return getTypeString(); }
}
