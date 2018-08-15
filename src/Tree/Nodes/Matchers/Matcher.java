package Tree.Nodes.Matchers;

import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Leaf;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public abstract class Matcher extends Leaf {

    protected final char name;

    Matcher(char name){
        this.name = name;
        isMatcher = true;
    }

    public char getName(){
        return name;
    }

    public boolean matcherMatches(Node node){
        switch(getType()){
            case Any: return true;
            default: return false;
        }
    }

    public abstract int sameTypeCompare(Node node);
    public abstract String toString();
    public abstract String getTypeString();
    public abstract Type getType();
    public BigDecimal getDecimalValue() {return null;}
    public Fraction getFractionalValue() {return null;}
}
