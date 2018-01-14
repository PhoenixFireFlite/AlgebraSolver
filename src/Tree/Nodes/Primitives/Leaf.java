package Tree.Nodes.Primitives;
import Tree.Nodes.NodeType;

public abstract class Leaf extends Node{

    @Override
    public abstract int sameTypeCompare(Node node);

    public Node[] getChildren(){
        return null;
    }
    public int getChildrenCount() {return 0;}
    public NodeType getNodeType(){ return NodeType.Leaf; }

}
