package Tree.Nodes.Primitives;
import Tree.Nodes.NodeType;

public abstract class Binary extends Node{

    private Node[] children = new Node[2];

    protected Binary(){}
    public Binary(Node a, Node b){
        setChildren(a, b);
    }

    private void setChildren(Node a, Node b){
        a.setParent(this);
        b.setParent(this);

        children[0] = a;
        children[1] = b;
    }

    public String toString(){
        String str = getTypeString();
        int prec = getPrecedence(str);

        Node[] children = getChildren();

        String a = (getNodePrecedence(children[0]) <= prec && children[0].getType() != getType())
                ? "("+children[0].toString()+")" : children[0].toString();

        String b = (getNodePrecedence(children[1]) <= prec && children[1].getType() != getType())
                ? "("+children[1].toString()+")" : children[1].toString();

        return a+str+b;
    }

    public void setChildTo(Node from, Node to){
        if(children[0].equalTo(from)){
            to.setParent(this);
            children[0] = to;
        }
        if(children[1].equalTo(from)){
            to.setParent(this);
            children[1] = to;
        }
    }

    public Node[] getChildren(){
        return children;
    }
    public int getChildrenCount() {return 2;}

    public abstract Node getCopy();

    public NodeType getNodeType(){
        return NodeType.Binary;
    }
}
