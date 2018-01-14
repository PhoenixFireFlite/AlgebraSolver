package Tree.Nodes.Operands.Constants;

import Tree.Nodes.CONSTANTS;
import Tree.Nodes.Functions.Advanced.BigDecimalMath;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class E extends Leaf {

    private static BigDecimal E_value = BigDecimalMath.e(CONSTANTS.MathConstantsMathContext);
    private static Fraction E_fraction = new Fraction(E_value);

    public E(){ }

    @Override
    public int sameTypeCompare(Node node) { return 0; }

    @Override
    public boolean sameTypeEquals(Node node) { return true; }

    public String getTypeString(){
        return "E";
    }

    public BigDecimal getDecimalValue() { return E_value; }

    public Fraction getFractionalValue() { return E_fraction; }

    public Type getType(){
        return Type.E;
    }

    public String toString(){ return getTypeString(); }
}
