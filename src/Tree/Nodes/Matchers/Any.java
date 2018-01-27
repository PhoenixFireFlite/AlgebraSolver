package Tree.Nodes.Matchers;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Any extends Matcher {

    public Any(char name){
        super(name);
    }

    @Override
    public int sameTypeCompare(Node node) {
        return -Integer.compare(name - ((Any)node).name, 0);
    }

    @Override
    public boolean sameTypeEquals(Node node) {
        return name == ((Any)node).name;
    }

    public String getTypeString(){ return "ANY"; }

    public Node getCopy(){
        return new Any(name);
    }

    public Type getType(){
        return Type.Any;
    }

    public String toString(){
        return "ANY("+name+")";
    }
}
