package Tree.Nodes.Matchers;

import Tree.Nodes.NodeType;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Primitives.Node;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public class Scope extends Node {

    private long scopeID;


    public Scope() { super(); scopeID = getID();}

    public Scope(long scopeID) {super(); this.scopeID = scopeID;}

    public long getScopeID(){
        return scopeID;
    }

    public Scope getScopeCopy(){ // Reminder: used because hashmap will overwrite if not copied
        return new Scope(scopeID);
    }

    public String toString() { return getTypeString(); }
    public void setChildTo(Node from, Node to) { }
    public Node[] getChildren() { return new Node[0]; }
    public int getChildrenCount() { return 0; }
    public String getTypeString() { return "<"+Long.toHexString(scopeID)+">"; }
    public Node getCopy() { return null; }
    public Type getType() { return Type.Scope; }
    public NodeType getNodeType() { return NodeType.Leaf; }
    public BigDecimal getDecimalValue() { return null; }
    public Fraction getFractionalValue() { return null; }
}
