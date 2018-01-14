package Tree.Nodes.Primitives;

import Tree.Nodes.NodeType;
import Tree.Nodes.Operands.Fraction;
import Tree.Nodes.Type;

import java.math.BigDecimal;

public abstract class Node {
    private Node parent;

    void setParent(Node node){
        parent = node;
    }
    public Node getParent(){
        return parent;
    }

    private static String stringRepeat(String toRepeat, int times){
        StringBuilder sb = new StringBuilder("");
        times = (times < 0)? 0 : times;
        for(int i=0;i<times;i++)
            sb.append(toRepeat);
        return sb.toString();
    }

    private String toString_recursive(Node node, int depth){
        StringBuilder sb = new StringBuilder("");

        String spacing = stringRepeat(" ",depth-1);
        spacing += (depth == 0)? "" : "|";

        System.out.println(spacing+node.getTypeString());

        if(node.getChildrenCount() > 0) {
            for (Node n : node.getChildren()) {
                sb.append(toString_recursive(n, depth + 1));
            }
        }

        return sb.toString();
    }

    public int compare(Node node){
        int diff = getType().ordinal() - node.getType().ordinal();
        if(diff == 0){
            return sameTypeCompare(node);
        }else
            return (diff > 0)? -1 : 1;
    }

    public int sameTypeCompare(Node node){
        int diff = getChildrenCount() - node.getChildrenCount();
        if(diff == 0){
            Node[] nodeChildren = node.getChildren();
            Node[] children = getChildren();
            for(int i=0;i<getChildrenCount();i++){
                int compared = children[i].compare(nodeChildren[i]);
                if(compared != 0) return compared;
            }
            return 0;
        }else{
            return (diff > 0)? -1 : 1;
        }
    }

    public boolean equalTo(Node node) {
        return (getType().ordinal() - node.getType().ordinal() == 0) && sameTypeEquals(node);
    }

    public boolean sameTypeEquals(Node node){
        int diff = getChildrenCount() - node.getChildrenCount();
        if(diff == 0){
            Node[] nodeChildren = node.getChildren();
            Node[] children = getChildren();
            for(int i=0;i<getChildrenCount();i++){
                if(!children[i].equalTo(nodeChildren[i]))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static int getPrecedence(String s){
        switch (s){
            case "+": return 0;
            case "-": return 0;
            case "*": return 5;
            case "/": return 5;
            case "^": return 10;
            case "(": return -10;
            default: System.err.println("Unknown precedence: "+s); return -1;
        }
    }

    public static int getNodePrecedence(Node n){
        if(n.getNodeType() == NodeType.Leaf || n.getNodeType() == NodeType.Unary){
            return 100;
        }else{
            return getPrecedence(n.getTypeString());
        }
    }

    public String getBranches(){
        return toString_recursive(this, 0);
    }

    public abstract String toString();

    public abstract Node[] getChildren();
    public abstract int getChildrenCount();
    public abstract String getTypeString();

    public abstract Type getType();
    public abstract NodeType getNodeType();

    public abstract BigDecimal getDecimalValue();
    public abstract Fraction getFractionalValue();

}
