package Tree.Nodes.Operands;

import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Variable extends Leaf {
    private final char name;

    public Variable(char name){
        this.name = name;
    }

    @Override
    public int sameTypeCompare(Node node) {
        return -Integer.compare(name - ((Variable)node).name, 0);
    }

    @Override
    public boolean sameTypeEquals(Node node) {
        return name == ((Variable)node).name;
    }

    public String getTypeString(){ return Character.toString(name); }

    public BigDecimal getDecimalValue() { return null; }

    public Fraction getFractionalValue() { return null; }

    public Type getType(){
        return Type.Variable;
    }

    public String toString(){
        return getTypeString();
    }
}
