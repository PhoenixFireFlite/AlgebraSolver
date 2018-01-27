package Tree.Nodes.Primitives;

import Tree.Nodes.NodeType;

public abstract class Unary extends Node{

    private Node[] children = new Node[1];

    public Unary(){}
    public Unary(Node node){
        setChild(node);
    }

    private void setChild(Node node){
        node.setParent(this);
        children[0] = node;
    }

    public void setChildTo(Node from, Node to){
        if(children[0].equalTo(from))
            setChild(to);
    }

    public Node[] getChildren(){
        return children;
    }
    public int getChildrenCount() {return 1;}

    public abstract Node getCopy();

    public NodeType getNodeType(){
        return NodeType.Unary;
    }

}
