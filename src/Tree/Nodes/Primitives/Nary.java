package Tree.Nodes.Primitives;

import java.util.ArrayList;
import Tree.Nodes.NodeType;

public abstract class Nary extends Node{

    private ArrayList<Node> children = new ArrayList<>();

    public Nary(){}
    public Nary(Node... nodes){
        addChildren(nodes);
    }
    public Nary(Node[]... nodess) {for(Node[] n: nodess) {addChildren(n);}}
    public Nary(Node[] nodeArray, Node node) {addChildren(nodeArray);addChild(node);}

    public void addChildren(Node... nodes){
        for(Node node: nodes)
            addChild(node);
    }

    public void clearChildren(){
        children.clear();
    }

    public void addChild(Node node){
        if(node.getType() == getType()){
            addChildren(node.getChildren());
        }else {
            node.setParent(this);
            addSorted(node);
        }
    }

    private void addSorted(Node node){
        for(int i=0;i<children.size();i++){
            if(node.compare(children.get(i)) > 0) {
                children.add(i, node);
                return;
            }
        }
        children.add(node);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        String str = getTypeString();
        int prec = getPrecedence(str);

        for(Node child: getChildren()){
            if(getNodePrecedence(child) <= prec){
                builder.append('(').append(child).append(')');
            }else{
                builder.append(child);
            }
            builder.append(str);
        }
        builder.deleteCharAt(builder.length()-1);

        return builder.toString();
    }

    public Node[] getChildren(){
        Node[] a = new Node[children.size()];
        return children.toArray(a);
    }
    public int getChildrenCount() {return children.size();}

    public abstract Node getEmptyCopy();

    public NodeType getNodeType(){
        return NodeType.Nary;
    }

}
