package Tree.Nodes.Operands.Constants;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class I extends Leaf {

    public I(){ }

    @Override
    public int sameTypeCompare(Node node) { return 0; }

    @Override
    public boolean sameTypeEquals(Node node) { return true; }

    public String getTypeString(){
        return "I";
    }

    public BigDecimal getDecimalValue() { return null; }

    public Fraction getFractionalValue() { return null; }

    public Type getType(){
        return Type.I;
    }

    public String toString(){ return getTypeString(); }
}
