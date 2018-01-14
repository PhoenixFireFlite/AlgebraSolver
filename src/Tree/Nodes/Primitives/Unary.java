package Tree.Nodes.Primitives;

import Tree.Nodes.NodeType;

public abstract class Unary extends Node{

    private Node[] children = new Node[1];

    public Unary(){}
    public Unary(Node node){
        setChild(node);
    }

    public void setChild(Node node){
        node.setParent(this);
        children[0] = node;
    }

    public Node[] getChildren(){
        return children;
    }
    public int getChildrenCount() {return 1;}

    public abstract Node getEmptyCopy();

    public NodeType getNodeType(){
        return NodeType.Unary;
    }

}
